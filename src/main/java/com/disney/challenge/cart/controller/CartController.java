package com.disney.challenge.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.disney.challenge.cart.model.Article;
import com.disney.challenge.cart.model.Cart;
import com.disney.challenge.cart.repository.ArticleRepository;
import com.disney.challenge.cart.repository.CartRepository;
import com.disney.challenge.cart.repository.Exception.ArticleNotFoundException;
import com.disney.challenge.cart.repository.Exception.CartNotFoundException;
import com.disney.challenge.cart.util.Constants;


@RestController
@RequestMapping("/cart")
public class CartController {

	private final CartRepository cartRepository;
	private final ArticleRepository articleRepository;
	private final RestTemplate restTemplate;
	
	@Autowired
	public CartController(CartRepository cartRepository, ArticleRepository articleRepository, RestTemplateBuilder restTemplateBuilder) {
		this.cartRepository = cartRepository;
		this.articleRepository = articleRepository;
		this.restTemplate = restTemplateBuilder.build();
	}
	
	@GetMapping("/{id}")
	public Cart getCart(@PathVariable Long id) {
		return this.cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
	}
	
	@PostMapping
	public ResponseEntity<Cart> createCart() {
		Cart cart = this.cartRepository.save(new Cart());
		return ResponseEntity.ok(cart);
	}
	
	@PutMapping("/{id}/{articleId}")
	public Cart updateCart(@PathVariable Long id, @PathVariable Long articleId) {
		Cart cart = this.cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
		if(this.articleRepository.findById(articleId).isPresent()) {
			Article article = this.articleRepository.findById(articleId).get();
			article.increaseQuantity();
			this.articleRepository.save(article);
		}else {
			getArticle(articleId, cart);
		}
		cart.updateTotalAmount();
		
		return this.cartRepository.save(cart);
	}
	
	@DeleteMapping("/{id}/{articleId}")
	public ResponseEntity<Cart> removeArticleFromCart(@PathVariable Long id, @PathVariable Long articleId) {
		Cart cart = this.cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
		Article article = cart.getArticleList().stream().filter(a -> a.getId().equals(articleId)).findFirst().orElseThrow(()-> new ArticleNotFoundException(articleId));
		if(article.getQuantity() > 1) {
			article.decreaseQuantity();
			cart.updateTotalAmount();
			this.articleRepository.save(article);
		}else {
			cart.removeArticle(articleId);
			this.articleRepository.delete(article);
			cart.updateTotalAmount();
			this.cartRepository.save(cart);
		}
		
		return ResponseEntity.ok(cart);
	}

	private Article getArticle(Long articleId, Cart cart) {
		
		try {
			Article article = restTemplate.getForObject(Constants.ARTICLES_API_PATH+"/"+articleId, Article.class);
			article.setCart(cart);
			article.increaseQuantity();
			articleRepository.save(article);
			return article;
		}catch(RestClientException e) {
			throw new ArticleNotFoundException(articleId);
		}
	}
	
	
	
}

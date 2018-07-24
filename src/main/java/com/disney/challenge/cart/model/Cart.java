package com.disney.challenge.cart.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.util.CollectionUtils;

@Entity
public class Cart {
	
	@Id
	@GeneratedValue
	private Long id;
	@OneToMany(mappedBy= "cart")
	private List<Article> articleList;
	private Double totalAmount;
	
	public Cart(Long id) {
		this.id = id;
	}
	
	public Cart() {
		
	}
	public Long getId() {
		return id;
	}

	public List<Article> getArticleList() {
		return articleList;
	}
	
	public Double getTotalAmount() {
		return totalAmount;
		
	}
	
	public void addArticle(Article article) {
		if(CollectionUtils.isEmpty(articleList)) {
			articleList = Arrays.asList(article);
		}else {
			articleList.add(article);
		}
	}
	
	public void updateTotalAmount() {
		if (!CollectionUtils.isEmpty(articleList)) {
			totalAmount = this.articleList.stream().map(Article::getPrice).collect(Collectors.toList()).stream()
					.mapToDouble(price -> price.doubleValue()).sum();
		}else {
			totalAmount =0D;
		}

	}
	
	public void removeArticle(Long articleId) {
		articleList = articleList.stream().filter(a -> !a.getId().equals(articleId)).collect(Collectors.toList());
		
	}

}

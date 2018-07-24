package com.disney.challenge.cart.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.disney.challenge.cart.App;
import com.disney.challenge.cart.model.Cart;
import com.disney.challenge.cart.repository.ArticleRepository;
import com.disney.challenge.cart.repository.CartRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class CartControllerTest {
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	private MockMvc mockMvc;
	
	private Cart cart;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		this.articleRepository.deleteAllInBatch();
		this.cartRepository.deleteAllInBatch();
		
		this.cart = this.cartRepository.save(new Cart());
	}
	
	@Test
	public void cartNotFoundTest() throws Exception {
		mockMvc.perform(get("/cart/3/")
				.contentType(contentType))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void getCartInfoTest() throws Exception {
		mockMvc.perform(get("/cart/1"))
			            .andDo(print())
			            .andExpect(status().isOk())
			            .andExpect(content().contentType(contentType))
			            .andExpect(jsonPath("$.id", is(this.cart.getId().intValue())))
			            .andExpect(jsonPath("$.totalAmount", is(this.cart.getTotalAmount())));
		
		
	}
	
	@Test
	public void createCartTest() throws Exception{
		mockMvc.perform(post("/cart"))		                 
			            .andDo(print())
			            .andExpect(status().isOk())
			            .andExpect(content().contentType(contentType));
			            
	}
	
	

}

package com.disney.challenge.cart.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Article {
	
	@Id
	private Long id;
	private String title;
	private Double price;
	private int quantity;
	@JsonIgnore
	@ManyToOne
	private Cart cart;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getPrice() {
		return price * quantity;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
	public void increaseQuantity() {
		quantity ++;
	}
	
	public void decreaseQuantity() {
		if(quantity >= 1) {
			quantity --;
		}
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}
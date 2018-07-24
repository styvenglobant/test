package com.disney.challenge.cart.repository.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public CartNotFoundException(Long cartId){
		super("Could not find cart " + cartId+" .");
	}

}

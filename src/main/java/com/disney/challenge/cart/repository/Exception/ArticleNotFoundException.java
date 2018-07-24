package com.disney.challenge.cart.repository.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ArticleNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ArticleNotFoundException(Long articleId) {
		super("Could not find article "+ articleId +" .");
	}

}

package com.disney.challenge.cart.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.disney.challenge.cart.util.ErrorJson;


@RestController
public class GenericErrorController implements ErrorController{
	
	private static final String PATH = "/error";
	private final ErrorAttributes errorAttributes;
	
	@Autowired
	public GenericErrorController(ErrorAttributes errorAttributes) {
		Assert.notNull(errorAttributes,"ErrorAttributes must not be null");
		this.errorAttributes = errorAttributes;
		
	}
	 
	@RequestMapping(value = PATH)
    public ErrorJson error(HttpServletRequest request, WebRequest webRequest, HttpServletResponse response) {
		 // Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring. 
        // Here we just define response body.
		
        return new ErrorJson(response.getStatus(), getErrorAttributes(webRequest, getTraceParameter(request)));
    }

	private boolean getTraceParameter(HttpServletRequest request) {
	    String parameter = request.getParameter("trace");
	    if (parameter == null) {
	        return false;
	    }
	    return !"false".equals(parameter.toLowerCase());
	  }
	
	 private Map<String, Object> getErrorAttributes( WebRequest webRequest, boolean includeStackTrace) {
		    return errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
		  }
	 
	@Override
	public String getErrorPath() {
		return PATH;
	}

}

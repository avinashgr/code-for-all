package com.restr.api.entity.http;

import org.apache.http.HttpHeaders;
/**
 * Composite enum for all the headers that 
 * can be used during a ReST API call
 * @author aranjalkar
 *
 */
public enum HttpStandardHeaders {
	ACCEPT(HttpHeaders.ACCEPT), 
	ACCEPT_CHARSET(HttpHeaders.ACCEPT_CHARSET),
    ACCEPT_ENCODING(HttpHeaders.ACCEPT_ENCODING), 
    CONTENT_TYPE(HttpHeaders.CONTENT_TYPE), 
    AUTHORIZATION(HttpHeaders.AUTHORIZATION);
	private String value;
	HttpStandardHeaders(String name){
		this.value=name;
	}
}
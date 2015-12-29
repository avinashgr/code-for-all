package com.covisint.iot.services.exception;

public class APIException extends Exception {
	private final String callStatus;
	private final String idmAPIError;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2383661264545860384L;
	
	public APIException(final String status,final String message) {
		this.callStatus = status;
		this.idmAPIError = message;
	}

}


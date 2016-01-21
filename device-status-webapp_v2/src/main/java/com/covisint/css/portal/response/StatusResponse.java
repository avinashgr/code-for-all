package com.covisint.css.portal.response;

public class StatusResponse {
	private String status;
	private String statusCode;
	private String statusDescription;
	public String getStatus() {
		return status;
	}
	public StatusResponse setStatus(String status) {
		this.status = status;
		return this;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public StatusResponse setStatusCode(String statusCode) {
		this.statusCode = statusCode;
		return this;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	public StatusResponse setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
		return this;
	}

}

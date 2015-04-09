package com.covisint.cisco.api.entity;

import java.util.Map;

public class APIResponse {
	//supported HTTP methods
	public static enum HttpMethod {GET, POST, PUT,DELETE};
	private ProxyInfo httpProxy;
	private ProxyInfo httpsProxy;
	private Map<String, String> headerParams;
	private Map<String, String> requestParams;
	private Map<String, String> updateParams;
	private HttpMethod requestMethod;
	private String apiUrl;
	private String formEncoding;
	private String responseContent;
	private String requestContent;

	
	
	public Map<String, String> getHeaderParams() {
		return headerParams;
	}
	public void setHeaderParams(Map<String, String> headerParams) {
		this.headerParams = headerParams;
	}
	public Map<String, String> getRequestParams() {
		return requestParams;
	}
	public void setRequestParams(Map<String, String> requestParams) {
		this.requestParams = requestParams;
	}
	public String getResponseContent() {
		return responseContent;
	}
	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}
	public String getRequestContent() {
		return requestContent;
	}
	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}
	public HttpMethod getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(HttpMethod requestMethod) {
		this.requestMethod = requestMethod;
	}
	public String getApiUrl() {
		return apiUrl;
	}
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	public String getFormEncoding() {
		return formEncoding;
	}
	public void setFormEncoding(String formEncoding) {
		this.formEncoding = formEncoding;
	}
	public ProxyInfo getHttpsProxy() {
		return httpsProxy;
	}
	public void setHttpsProxy(ProxyInfo httpsProxy) {
		this.httpsProxy = httpsProxy;
	}
	public ProxyInfo getHttpProxy() {
		return httpProxy;
	}
	public void setHttpProxy(ProxyInfo httpProxy) {
		this.httpProxy = httpProxy;
	}
	public Map<String, String> getUpdateParams() {
		return updateParams;
	}
	public void setUpdateParams(Map<String, String> updateParams) {
		this.updateParams = updateParams;
	}

	
}

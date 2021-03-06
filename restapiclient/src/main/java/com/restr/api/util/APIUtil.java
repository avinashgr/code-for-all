package com.restr.api.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.restr.api.entity.APIResponse;
import com.restr.api.entity.ProxyInfo;
import com.restr.api.entity.http.HttpMethod;

public class APIUtil {
	
	private static Logger  logger = LoggerFactory.getLogger(APIUtil.class);
    /**
     * sets the proxy if proxyInfo has data
     * @param proxyInfo
     * @param content
     */
	public static ProxyInfo setProxy(String proxyInfo) {
		if(null!=proxyInfo && proxyInfo.trim().length()>0){
	     ProxyInfo proxy = new ProxyInfo().setProxyDNS(proxyInfo.split(":")[0])
	    		 .setProxyPort(Integer.parseInt(proxyInfo.split(":")[1])); 
	     return proxy;
		}else{
			return null;
		}
	}
	
	/**
	 * set the request params into the header and form
	 * @param params
	 * @param content
	 * @return
	 */
	public static Map<String, String> setRequestParams(List<String>[] params,
			APIResponse content) {
		 Map<String, String> headerInfo = APIUtil.addSplitParams(params[0]);
		 content.setHeaderParams(headerInfo);
		 Map<String, String> formInfo = APIUtil.addSplitParams(params[1]);
		 content.setRequestParams(formInfo);
		 content.setApiUrl(params[2].get(0));
		 return formInfo;
	}
	/**
	 * Takes the OAUTH content and updates the content with the 
	 * response
	 * @param content
	 * @return
	 */
	public static APIResponse processRequest(APIResponse content){
		
		switch (content.getRequestMethod()){
		case GET:
			getRequest(content);
			break;
		case POST:
			postRequest(content);
			break;
		case PUT:
			putRequest(content);
			break;
		case DELETE:
			deleteRequest(content);
		default:
			break;
		
		}
			
		return content;		
	}
	/**
	 * Processes the oauth content to do a get
	 * @param content
	 */
	private static void getRequest(APIResponse content) {
		CloseableHttpClient httpclient = getAllCertsTrustingClient(content);
		addBodyParamsToUrl(content);
		HttpGet httpPost = new HttpGet(content.getApiUrl());
		RequestConfig config = setProxy(content);
		if (null != config) {
			httpPost.setConfig(config);
		}
		addHeaderParams(content, httpPost);
//		addBodyParams(content, httpPost);
		readResponse(content, httpclient, httpPost);
		
	}

	/**
	 * Processes the oauth content to do a get
	 * @param content
	 */
	private static void postRequest(APIResponse content) {
		CloseableHttpClient httpclient = getAllCertsTrustingClient(content);
		HttpPost httpPost = new HttpPost(content.getApiUrl());
		RequestConfig config = setProxy(content);
		if (null != config) {
			httpPost.setConfig(config);
		}
		addJSONBody(content,httpPost);
		addBodyParams(content, httpPost);
		addHeaderParams(content, httpPost);
		readResponse(content, httpclient, httpPost);
	}
	
	/**
	 * Processes the oauth content to do a get
	 * @param content
	 */
	private static void putRequest(APIResponse content) {
		CloseableHttpClient httpclient = getAllCertsTrustingClient(content);
		HttpPut httpPost = new HttpPut(content.getApiUrl());
		httpPost.setHeader("accept-charset", "ISO-8859-1,*,utf-8");
		RequestConfig config = setProxy(content);
		if (null != config) {
			httpPost.setConfig(config);
		}
		addJSONBody(content,httpPost);
		addBodyParams(content, httpPost);
		addHeaderParams(content, httpPost);
		readResponse(content, httpclient, httpPost);
	}
	private static void addJSONBody(APIResponse content, HttpRequestBase httpPost) {
		if(null==content.getJsonBody() || content.getJsonBody().length()<=0){
			return;
		}
		try{
			StringEntity input = new StringEntity(content.getJsonBody());
			if (httpPost instanceof HttpPost){
				((HttpPost)httpPost).setEntity(input);
			}else if(httpPost instanceof HttpPut){
				((HttpPut)httpPost).setEntity(input);
			}			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	private static CloseableHttpClient getAllCertsTrustingClient(APIResponse content){		
		CloseableHttpClient httpclient;
		try{
		    httpclient = trustAllCerts(content);
			return httpclient;
		}catch(KeyStoreException e){
			logger.error("Certificate trust failure: keystore: mostly a SSL encryption issue",e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Certificate trust failure: no such alg: mostly a SSL encryption issue",e);
		} catch (KeyManagementException e) {
			logger.error("Certificate trust failure: key managment: mostly a SSL encryption issue",e);
		}
		return HttpClients.createDefault(); 
	}
	/**
	 * Trusts all certs for https
	 * @param builder
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws KeyManagementException
	 */
	private static CloseableHttpClient trustAllCerts(APIResponse content)
			throws NoSuchAlgorithmException, KeyStoreException,
			KeyManagementException {
		CloseableHttpClient httpclient;
		SSLContextBuilder builder = new SSLContextBuilder();
		builder.loadTrustMaterial(null, new TrustStrategy(){
			public boolean isTrusted(
					java.security.cert.X509Certificate[] chain,
					String authType)
					throws java.security.cert.CertificateException {
				return true;
			}
		 });

		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		        builder.build());

		httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();


		return httpclient;
	}
	private static void readResponse(APIResponse content,
			CloseableHttpClient httpclient, HttpRequestBase httpPost) {
		try{
		// Create a custom response handler
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

		    public String handleResponse(
		            final HttpResponse response) throws ClientProtocolException, IOException {
		        int status = response.getStatusLine().getStatusCode();
		        Header[] headers =response.getAllHeaders();
		        
		        if (status >= 200 && status < 300) {
		            HttpEntity entity = response.getEntity();
		            return entity != null ? EntityUtils.toString(entity,"UTF-8") : null;
		        } else {
		        	logger.debug(EntityUtils.toString(response.getEntity()));
		            throw new ClientProtocolException("Unexpected response status: " + status);
		        }
		    }

		};
		

		String responseBody = httpclient.execute(httpPost,responseHandler);
//		logger.debug("Responsebody:"+responseBody);
		content.setResponseContent(responseBody);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	private static void addHeaderParams(APIResponse content, HttpRequestBase httpPost) {

		if(null!=content.getHeaderParams()){
			if (content.getUserName()!=null && (content.getUserName()).length() >0){
				byte[] authEncBytes = Base64.encodeBase64((content.getUserName()+":"+content.getPassword()).getBytes());
				String authStringEnc = new String(authEncBytes);
				logger.debug("Base64 encoded auth string: " + authStringEnc);
				content.getHeaderParams().put("Authorization","Basic "+authStringEnc);
			}
			for (Entry<String, String> entry : content.getHeaderParams().entrySet())
			{
			    logger.debug(entry.getKey() + "/" + entry.getValue());
			    httpPost.addHeader(entry.getKey(), entry.getValue());
			}
		}
	}
	private static void addBodyParams(APIResponse content, HttpRequestBase httpPost){
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		if(null!=content.getJsonBody()&&content.getJsonBody().length()>0){
			return;
		}
		if(null!=content.getRequestParams()){
			for (Entry<String, String> entry : content.getRequestParams().entrySet())
			{
			    logger.debug(entry.getKey() + "/" + entry.getValue());
			    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		try {
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps);
			if (httpPost instanceof HttpPost){
				((HttpPost)httpPost).setEntity(formEntity);
			}else if(httpPost instanceof HttpPut){
				((HttpPut)httpPost).setEntity(formEntity);
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void addBodyParamsToUrl(APIResponse content){
		String url = content.getApiUrl();
		if(null!=content.getRequestParams()){
			url+="?";
			for (Entry<String, String> entry : content.getRequestParams().entrySet())
			{
			    logger.debug(entry.getKey() + "/" + entry.getValue());
			    url+=entry.getKey()+"="+entry.getValue()+"&";
			}
			url = url.substring(0, url.length()-1);
		}
		
		try {
			content.setApiUrl(url);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static RequestConfig setProxy(APIResponse content) {
		if(null!=content.getHttpsProxy() || null!=content.getHttpProxy()){
//			HttpHost target = new HttpHost("localhost", 443, "https");
			HttpHost proxy = new HttpHost(content.getHttpProxy().getProxyDNS(), 
					content.getHttpProxy().getProxyPort(), 
					"http");
	        RequestConfig config = RequestConfig.custom()
	                .setProxy(proxy)
	                .build();
	        return config;
		}else{
			return null;
		}
		
	}

	public static Map<String, String> addSplitParams(List<String> params) {
		Map<String, String> paramInfo = new HashMap<String,String>();
		for(String param: params){
			paramInfo.put(param.split(":")[0], param.split(":")[1]);
		}
		return paramInfo;
	}

	
	/**
	 * Processes the oauth content to do a get
	 * @param content
	 */
	private static void deleteRequest(APIResponse content) {
		CloseableHttpClient httpclient = getAllCertsTrustingClient(content);
		HttpDelete httpPost = new HttpDelete(content.getApiUrl());
		httpPost.setHeader("accept-charset", "ISO-8859-1,*,utf-8");
		RequestConfig config = setProxy(content);
		if (null != config) {
			httpPost.setConfig(config);
		}
		addBodyParams(content, httpPost);
		addHeaderParams(content, httpPost);
		readResponse(content, httpclient, httpPost);
	}
	
	public static APIResponse processAndPrintResponse(List<String>[] params,HttpMethod method,ProxyInfo proxy) {
		APIResponse content = new APIResponse();
		 Map<String, String> formInfo = APIUtil.setRequestParams(params, content);
		 content.setRequestParams(formInfo);
		 content.setRequestMethod(method);
	     content.setHttpProxy(proxy);
		 content.setHttpsProxy(proxy);	
		return content;
	}

	public static void setProxy(APIResponse response, ProxyInfo proxy) {		
			response.setHttpProxy(proxy);
			response.setHttpsProxy(proxy);
			setProxy(response);		
	}
}

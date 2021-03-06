package com.covisint.cisco.api;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ch.qos.logback.classic.BasicConfigurator;
import ch.qos.logback.classic.LoggerContext;

import com.restr.api.entity.APIResponse;
import com.restr.api.entity.ProxyInfo;
import com.restr.api.entity.UserToken;
import com.restr.api.entity.http.HttpMethod;
import com.restr.api.util.APIUtil;
import com.restr.api.util.JSONUtil;

public class TestAPI {
	
  private ProxyInfo proxy;
  private static Logger  logger = LoggerFactory.getLogger(TestAPI.class);

 
    /**
     * sets the proxy if proxyInfo has data
     * @param proxyInfo
     * @param content
     */
	private ProxyInfo setProxy(String proxyInfo) {
		if(null!=proxyInfo && proxyInfo.trim().length()>0){
	     ProxyInfo proxy = new ProxyInfo().setProxyDNS(proxyInfo.split(":")[0])
	    		 .setProxyPort(Integer.parseInt(proxyInfo.split(":")[1])); 
	     return proxy;
		}else{
			return null;
		}
	}
	@BeforeTest
	 public void init(){
		BasicConfigurator.configure(new LoggerContext());
		proxy =null;
		//in Covisint VPN using proxy
/*		proxy = new ProxyInfo().setProxyDNS(
				"10.66.1.108").setProxyPort(3128);*/
	 }
	
	  @Test
	  public void getToken() {
		APIResponse response = new APIResponse();		
		initializeTokenParams(response);
		response.setRequestMethod(HttpMethod.POST);
		setProxy(response);
		APIUtil.processRequest(response);
		logger.debug("Response:" + response.getResponseContent());
		UserToken token = JSONUtil.unmarshalResponse(response.getResponseContent(), new UserToken());
		logger.debug("*********************end of getToken");
	  }
	  

		private void initializeTokenParams(APIResponse response) {
			List<String> params = new ArrayList<String>();
			params.add("Content-Type:application/x-www-form-urlencoded");
			params.add("client_id:ab3ab1a476930b88d72779013ed15043");
			params.add("client_secret:0df4d7214ea11ead");
			response.setHeaderParams(APIUtil.addSplitParams(params));
			params  =  new ArrayList<String>();
			params.add("username:mozaiqadmin");
			params.add("grant_type:password");
			params.add("password:test1234");
			response.setRequestParams(APIUtil.addSplitParams(params));
			response.setApiUrl("https://api.qa.covisint.com/cisco/cis/mozaiqdev/v1/oauth2/token");
		}
	
		private void setProxy(APIResponse response) {
			response.setHttpProxy(proxy);
			response.setHttpsProxy(proxy);
		}
}

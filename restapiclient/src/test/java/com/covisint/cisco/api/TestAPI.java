package com.covisint.cisco.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.covisint.cisco.api.entity.APIResponse;
import com.covisint.cisco.api.entity.APIResponse.HttpMethod;
import com.covisint.cisco.api.entity.ProxyInfo;
import com.covisint.cisco.api.entity.UserToken;
import com.covisint.cisco.api.util.APIUtil;
import com.covisint.cisco.api.util.JSONUtil;

public class TestAPI {
	
  private ProxyInfo proxy;
  private static Logger  logger = Logger.getLogger(TestAPI.class);

 
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
		BasicConfigurator.configure();
		proxy =null;
		//in Covisint VPN using proxy
/*		proxy = new ProxyInfo().setProxyDNS(
				"10.66.1.108").setProxyPort(3128);*/
	 }
	
	  @Test
	  public void getToken() {
		List<String>[] params = initializeTokenParams();
		APIResponse response = new APIResponse();		
		APIUtil.setRequestParams(params,response);
		response.setRequestMethod(HttpMethod.POST);
		setProxy(response);
		APIUtil.processRequest(response);
		logger.debug("Response:" + response.getResponseContent());
		UserToken token = JSONUtil.unmarshalResponse(response.getResponseContent(), new UserToken());
		logger.debug("*********************end of getToken");
	  }
	  

		private List<String>[] initializeTokenParams() {
			List<String>[] params = new ArrayList[4];
			params[0] = new ArrayList<String>();
			params[0].add("Content-Type:application/x-www-form-urlencoded");
			params[0].add("client_id:ab3ab1a476930b88d72779013ed15043");
			params[0].add("client_secret:0df4d7214ea11ead");
			params[1] = new ArrayList<String>();
			params[1].add("username:mozaiqadmin");
			params[1].add("grant_type:password");
			params[1].add("password:test1234");
			params[2] = new ArrayList<String>();
			params[2].add("https://api.qa.covisint.com/cisco/cis/mozaiqdev/v1/oauth2/token");
			return params;
		}
		private void setProxy(APIResponse response) {
			response.setHttpProxy(proxy);
			response.setHttpsProxy(proxy);
		}
}

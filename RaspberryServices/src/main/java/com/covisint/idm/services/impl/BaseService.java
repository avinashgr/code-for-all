package com.covisint.idm.services.impl;

import com.restr.api.entity.APIResponse;
import com.restr.api.entity.ProxyInfo;
/**
 * Base utils for all services
 * @author aranjalkar
 *
 */
public abstract class BaseService {
	private ProxyInfo proxy;
	
	/**
	 * Sets the proxy
	 * @param response
	 */
	protected void setProxy(APIResponse response) {
		response.setHttpProxy(proxy);
		response.setHttpsProxy(proxy);		
	}
	
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
	/**
	 * @return 
	 * 
	 */
	abstract BaseService initHeaderParams(APIResponse api);

}

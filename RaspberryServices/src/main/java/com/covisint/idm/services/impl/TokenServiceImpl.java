package com.covisint.idm.services.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.covisint.idm.services.entities.token.Token;
import com.covisint.idm.services.interfaces.RestService;
import com.restr.api.entity.APIResponse;
import com.restr.api.entity.http.HttpMethod;
import com.restr.api.util.APIUtil;
import com.restr.api.util.JSONUtil;
@Component
@Qualifier("tokenService")
public class TokenServiceImpl extends BaseService implements RestService<Token,APIResponse> {
	private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
	public Token create(Token t,APIResponse r) {
		return t;
		// TODO Auto-generated method stub
		
	}

	public Token update(Token t,APIResponse r) {
		return t;
		// TODO Auto-generated method stub
		
	}

	public Token read(Token t,APIResponse r) {
		r.setRequestMethod(HttpMethod.POST);
		setProxy(r);
		APIUtil.processRequest(r);
		t= JSONUtil.unmarshalResponse(r.getResponseContent(), t);	
		logger.debug("Response:" + r.getResponseContent());
		return t;
	}

	public Token delete(Token t,APIResponse r) {
		return t;
		// TODO Auto-generated method stub
		
	}

	public List<Token> search(Token t, APIResponse r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TokenServiceImpl initHeaderParams(APIResponse api) {
		if(null==api.getHeaderParams()){
			api.setHeaderParams(new HashMap<String,String>());
		}
		api.getHeaderParams().put("Accept","application/vnd.com.covisint.platform.oauth.token.v1+json");
		api.getHeaderParams().put("type","client_credentials");	
		return this;
	}



	
	
	


}

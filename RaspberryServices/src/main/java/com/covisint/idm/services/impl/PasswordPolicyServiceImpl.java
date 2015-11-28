package com.covisint.idm.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.covisint.idm.services.entities.personv2.passwordv1.Password;
import com.covisint.idm.services.interfaces.RestService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.restr.api.entity.APIResponse;
import com.restr.api.entity.http.HttpMethod;
import com.restr.api.util.APIUtil;
import com.restr.api.util.JSONUtil;
@Component
@Qualifier("passwordPolicyService")
public class PasswordPolicyServiceImpl extends BaseService implements RestService<Password,APIResponse> {
	
	private static final Logger logger = LoggerFactory.getLogger(PasswordPolicyServiceImpl.class);
	
	public Password create(Password t,APIResponse r) {
		r.setRequestMethod(HttpMethod.POST);
		setProxy(r);
		APIUtil.processRequest(r);
		t= JSONUtil.unmarshalResponse(r.getResponseContent(), t);	
		logger.debug("Response:" + r.getResponseContent());
		return t;	
	}

	public Password update(Password t,APIResponse r) {
		r.setRequestMethod(HttpMethod.PUT);
		logger.debug("Password:"+r.getJsonBody());
		setProxy(r);
		APIUtil.processRequest(r);
		logger.debug("Response:" + r.getResponseContent());
		t= JSONUtil.unmarshalResponse(r.getResponseContent(), t);
		return t;		
	}

	public Password read(Password t,APIResponse r) {
		r.setRequestMethod(HttpMethod.GET);
		setProxy(r);
		APIUtil.processRequest(r);
		logger.debug("Response:" + r.getResponseContent());
		t= JSONUtil.unmarshalResponse(r.getResponseContent(), t);
		return t;
	}

	public Password delete(Password t,APIResponse r) {
		return t;
		// TODO Auto-generated method stub
		
	}

	public List<Password> search(Password t, APIResponse r) {
		r.setRequestMethod(HttpMethod.GET);
		setProxy(r);
		APIUtil.processRequest(r);
		logger.debug("Response:" + r.getResponseContent());
		List<Password> lt= new Gson().fromJson(r.getResponseContent(), new TypeToken<List<Password>>(){}.getType());
		return lt;
	}


	public PasswordPolicyServiceImpl initHeaderParams(APIResponse api) {
		api.getHeaderParams().put("Accept","application/vnd.com.covisint.platform.person.account.password.v1+json");
		api.getHeaderParams().put("Content-type","application/vnd.com.covisint.platform.person.account.password.v1+json");
		return this;
		
	}	


}

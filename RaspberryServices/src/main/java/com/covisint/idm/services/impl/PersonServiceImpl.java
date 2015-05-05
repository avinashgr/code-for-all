package com.covisint.idm.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.covisint.idm.services.entities.personv2.Person;
import com.covisint.idm.services.interfaces.RestService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.restr.api.entity.APIResponse;
import com.restr.api.entity.http.HttpMethod;
import com.restr.api.util.APIUtil;
import com.restr.api.util.JSONUtil;
@Component
@Qualifier("personService")
public class PersonServiceImpl extends BaseService implements RestService<Person,APIResponse> {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);
	
	public Person create(Person t,APIResponse r) {
		r.setRequestMethod(HttpMethod.POST);
		setProxy(r);
		APIUtil.processRequest(r);
		t= JSONUtil.unmarshalResponse(r.getResponseContent(), t);	
		logger.debug("Response:" + r.getResponseContent());
		return t;	
	}

	public Person update(Person t,APIResponse r) {
		return t;
		// TODO Auto-generated method stub
		
	}
	public Person activate(Person t,APIResponse r) {
		r.setRequestMethod(HttpMethod.POST);
		setProxy(r);
		APIUtil.processRequest(r);
		logger.debug("Response:" + r.getResponseContent());
		return t;
	}
	public Person read(Person t,APIResponse r) {
		r.setRequestMethod(HttpMethod.GET);
		setProxy(r);
		APIUtil.processRequest(r);
		logger.debug("Response:" + r.getResponseContent());
		t= JSONUtil.unmarshalResponse(r.getResponseContent(), t);
		return t;
	}

	public Person delete(Person t,APIResponse r) {
		return t;
		// TODO Auto-generated method stub
		
	}

	public List<Person> search(Person t, APIResponse r) {
		r.setRequestMethod(HttpMethod.GET);
		setProxy(r);
		APIUtil.processRequest(r);
		logger.debug("Response:" + r.getResponseContent());
		List<Person> lt= new Gson().fromJson(r.getResponseContent(), new TypeToken<List<Person>>(){}.getType());
		return lt;
	}
	
	
	


	public PersonServiceImpl initHeaderParams(APIResponse api) {
		api.getHeaderParams().put("Accept","application/vnd.com.covisint.platform.person.v1+json");
		api.getHeaderParams().put("Content-type","application/vnd.com.covisint.platform.person.v1+json");
		return this;
		
	}	


}

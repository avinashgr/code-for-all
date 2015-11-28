package com.covisint.idm.services.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.covisint.idm.services.entities.packagev2.ServicePackage;
import com.covisint.idm.services.entities.personv2.Person;
import com.covisint.idm.services.entities.twitter.TwitterResponse;
import com.covisint.idm.services.interfaces.RestService;
import com.restr.api.entity.APIResponse;
import com.restr.api.entity.http.HttpMethod;
import com.restr.api.util.APIUtil;
@Component
@Qualifier("twitterService")
public class TwitterServiceImpl extends BaseService implements RestService<TwitterResponse, APIResponse> {
	private static final Logger logger = LoggerFactory.getLogger(TwitterServiceImpl.class);
	@Override
	BaseService initHeaderParams(APIResponse api) {
		// TODO Auto-generated method stub
		return null;
	}
	public void sendMessage(Person p, ServicePackage sp,boolean tweet,String message) {
		logger.debug("Inside sendmessage"+message);
		APIResponse response  =new APIResponse();
		response.setRequestParams(new HashMap<String,String>());
		response.getRequestParams().put("status", "active");
		response.getRequestParams().put("deviceId", p.getId());
		response.getRequestParams().put("serviceId",sp.getId() );
		if(tweet){
			response.getRequestParams().put("tweetText", message);
		}
		response.setApiUrl("http://heartbeatapp.run.covapp.io/tasks/registerBeat");
		create(new TwitterResponse(),response );
	}

	public TwitterResponse create(TwitterResponse t, APIResponse r) {
		r.setRequestMethod(HttpMethod.POST);
		setProxy(r);
		APIUtil.processRequest(r);
		logger.debug("Response:" + r.getResponseContent());
		return t;
	}

	public TwitterResponse update(TwitterResponse t, APIResponse r) {
		// TODO Auto-generated method stub
		return null;
	}

	public TwitterResponse read(TwitterResponse t, APIResponse r) {
		// TODO Auto-generated method stub
		return null;
	}

	public TwitterResponse delete(TwitterResponse t, APIResponse r) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TwitterResponse> search(TwitterResponse t, APIResponse r) {
		// TODO Auto-generated method stub
		return null;
	}

}

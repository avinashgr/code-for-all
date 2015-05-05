package com.covisint.idm.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.covisint.idm.services.entities.packagev2.Packages;
import com.covisint.idm.services.entities.packagev2.ServicePackage;
import com.covisint.idm.services.entities.personv2.Person;
import com.covisint.idm.services.interfaces.RestService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.restr.api.entity.APIResponse;
import com.restr.api.entity.http.HttpMethod;
import com.restr.api.util.APIUtil;
import com.restr.api.util.JSONUtil;
@Component
@Qualifier("packageService")
public class PackageServiceImpl extends BaseService implements RestService<Person,APIResponse> {
	private static final Logger logger = LoggerFactory.getLogger(PackageServiceImpl.class);

	public Person create(Person t, APIResponse r) {
		// TODO Auto-generated method stub
		return null;
	}

	public Person update(Person t, APIResponse r) {
		r.setRequestMethod(HttpMethod.PUT);
		setProxy(r);
		APIUtil.processRequest(r);
		logger.debug("Response:" + r.getResponseContent());
		List<Packages> servicePkgs = new ArrayList<Packages>();
		servicePkgs.add(new Gson().fromJson(r.getResponseContent(),Packages.class ));
		t.setPackages(servicePkgs);
		return t;
	}

	public Person read(Person t, APIResponse r) {
		r.setRequestMethod(HttpMethod.GET);
		setProxy(r);
		APIUtil.processRequest(r);
		logger.debug("Response:" + r.getResponseContent());
		List<Packages> servicePkgs= new Gson().fromJson(r.getResponseContent(), new TypeToken<List<Packages>>(){}.getType());
		t.setPackages(servicePkgs);
		return t;
	}

	public Person delete(Person t, APIResponse r) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Person> search(Person t, APIResponse r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PackageServiceImpl initHeaderParams(APIResponse api) {
		api.getHeaderParams().put("Accept", "application/vnd.com.covisint.platform.package.grant.v1+json");
		api.getHeaderParams().put("Content-type","application/vnd.com.covisint.platform.package.grant.v1+json");
		return this;		
	}

}

package com.covisint.iot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.covisint.iot.services.exception.APIException;
import com.covisint.iot.services.vo.AttributeType;
import com.restr.api.entity.APIResponse;
@Component
@Qualifier("attrtypeclient")
public class AttributeTypeClient implements RestServiceClient<AttributeType, APIResponse> {

	public AttributeType create(AttributeType t, APIResponse r) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	public AttributeType update(AttributeType t, APIResponse r) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	public AttributeType read(AttributeType t, APIResponse r) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	public AttributeType delete(AttributeType t, APIResponse r) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<AttributeType> search(AttributeType t, APIResponse r) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

}

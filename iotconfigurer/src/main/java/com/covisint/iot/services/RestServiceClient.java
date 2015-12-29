package com.covisint.iot.services;


import java.util.List;

import com.covisint.iot.services.exception.APIException;
/**
 * Interface to indicate the service is a Restful Service Client
 *
 * @param <T>
 * @param <R>
 */
public interface RestServiceClient<T,APIResponse> {

		abstract T create(T t,APIResponse r) throws APIException;
		abstract T update(T t,APIResponse r)throws APIException;
		abstract T read(T t,APIResponse r)throws APIException;
		abstract T delete(T t,APIResponse r)throws APIException;
		abstract List<T> search(T t, APIResponse r)throws APIException;

}

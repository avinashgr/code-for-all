package com.covisint.idm.services.interfaces;

import java.util.List;

/**
 * 
 * @author aranjalkar
 *
 */
public interface RestService<T,R> {	
	abstract T create(T t,R r);
	abstract T update(T t,R r);
	abstract T read(T t,R r);
	abstract T delete(T t,R r);	
	abstract List<T> search(T t, R r);
}

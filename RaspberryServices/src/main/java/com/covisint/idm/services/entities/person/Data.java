package com.covisint.idm.services.entities.person;

import java.util.ArrayList;
import java.util.List;


public class Data {
	private String statusCode;
	private String subStatusCode;
	private List<Person> users;
	public List<Person> getUsers() {
		return users;
	}
	public void setUsers(List<Person> users) {
		this.users = users;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getSubStatusCode() {
		return subStatusCode;
	}
	public void setSubStatusCode(String subStatusCode) {
		this.subStatusCode = subStatusCode;
	}

}

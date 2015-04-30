package com.covisint.idm.services.impl;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.covisint.idm.services.entities.personv2.Person;

public class TestTokenService extends BaseTestService {
	static final Logger logger = LoggerFactory.getLogger(TestTokenService.class);
	@Test
	public void getToken() {
		IDMAPIServiceImpl piUtilService = (IDMAPIServiceImpl) appContext.getBean("piUtilService");
		logger.debug("Token found:"+piUtilService.getToken());
	}
	@Test
	public void createPerson(){
		IDMAPIServiceImpl piUtilService = (IDMAPIServiceImpl) appContext.getBean("piUtilService");
		Person resp = piUtilService.createPerson();
		logger.debug("Person found:"+(resp.getEmail()));
	}
	@Test
	public void updatePersonPassword(){
		IDMAPIServiceImpl piUtilService = (IDMAPIServiceImpl) appContext.getBean("piUtilService");
		Person resp = piUtilService.updatePersonPassword();
		logger.debug("Person found:"+(resp.getEmail()));
	}
	@Test
	public void getPersons() {
		IDMAPIServiceImpl piUtilService = (IDMAPIServiceImpl) appContext.getBean("piUtilService");
		List<Person> resp = piUtilService.getPersonInfo();
		for (Iterator<Person> iterator = resp.iterator(); iterator.hasNext();) {
			Person person = (Person) iterator.next();
			logger.debug("Person:::: found:"+(person.getEmail()));			
		}		
	}
	@Test
	public void getPerson() {
		IDMAPIServiceImpl piUtilService = (IDMAPIServiceImpl) appContext.getBean("piUtilService");
		Person resp = piUtilService.getPersonInfoWithID();
		logger.debug("Person found:"+(resp.getEmail()));
	}
	@Test
	public void getPersonServices() {
		IDMAPIServiceImpl piUtilService = (IDMAPIServiceImpl) appContext.getBean("piUtilService");
		Person resp = piUtilService.getPersonServices();
		logger.debug("Person found:"+(resp.toString()));
	}
/*	@Test
	public void testPersonJSON(){
		PersonResponse p =  new PersonResponse();
		Data d=  new Data();
		d.setStatusCode("200");
		d.setSubStatusCode("400");
		Person per = new Person();
		per.setFirstName("Avinash");
		List<Person> persons = new ArrayList<Person>();
		persons.add(per);
		d.setUsers(persons);
		p.setData(d);
		Gson gson = new Gson();
		System.out.println("data:"+gson.toJson(p));

	}*/

	

}

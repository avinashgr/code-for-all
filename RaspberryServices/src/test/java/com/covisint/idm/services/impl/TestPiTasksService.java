package com.covisint.idm.services.impl;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.covisint.idm.services.entities.personv2.Person;
import com.covisint.idm.services.scheduler.PiTasksServiceImpl;

public class TestPiTasksService extends BaseTestService {
	static final Logger logger = LoggerFactory.getLogger(TestPiTasksService.class);
	@Test
	public void getToken() {
		PiTasksServiceImpl piTasksService = (PiTasksServiceImpl) appContext.getBean("piTasksService");
		piTasksService.getToken();
	}
	@Test
	public void createPerson(){
		PiTasksServiceImpl piTasksService = (PiTasksServiceImpl) appContext.getBean("piTasksService");
		piTasksService.createPerson();
	}
	@Test
	public void getPersons() {
		PiTasksServiceImpl piTasksService = (PiTasksServiceImpl) appContext.getBean("piTasksService");
		piTasksService.getPersons();
	}
	@Test
	public void getPerson() {
		PiTasksServiceImpl piTasksService = (PiTasksServiceImpl) appContext.getBean("piTasksService");
		piTasksService.getPerson();
	}
	@Test
	public void getPersonServices() {
		PiTasksServiceImpl piTasksService = (PiTasksServiceImpl) appContext.getBean("piTasksService");
		piTasksService.getPersonServices();
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

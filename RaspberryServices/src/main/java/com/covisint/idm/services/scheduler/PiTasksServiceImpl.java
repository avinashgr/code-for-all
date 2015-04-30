package com.covisint.idm.services.scheduler;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.covisint.idm.services.entities.personv2.Person;
import com.covisint.idm.services.impl.IDMAPIServiceImpl;
@Component("piTasksService")
public class PiTasksServiceImpl {
	
	static final Logger logger = LoggerFactory.getLogger(PiTasksServiceImpl.class);
	
	@Autowired
	@Qualifier("piUtilService")
	private IDMAPIServiceImpl piUtilService;
	
	public void getToken() {
		logger.debug("Token found:"+piUtilService.getToken());
	}
	public void createPerson(){
		Person resp = piUtilService.createPerson();
		logger.debug("Person found:"+(resp.getEmail()));
	}
	public void getPersons() {
		List<Person> resp = piUtilService.getPersonInfo();
		for (Iterator<Person> iterator = resp.iterator(); iterator.hasNext();) {
			Person person = (Person) iterator.next();
			logger.debug("Person:::: found:"+(person.getEmail()));			
		}		
	}
	public void getPerson() {
		Person resp = piUtilService.getPersonInfoWithID();
		logger.debug("Person found:"+(resp.getEmail()));
	}
	public void getPersonServices() {
		Person resp = piUtilService.getPersonServices();
		logger.debug("Person found:"+(resp.toString()));
	}
}

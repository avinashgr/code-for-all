package com.covisint.idm.services.scheduler;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.covisint.idm.services.entities.packagev2.Packages;
import com.covisint.idm.services.entities.packagev2.ServicePackage;
import com.covisint.idm.services.entities.personv2.Person;
import com.covisint.idm.services.entities.pi.PiFunction;
import com.covisint.idm.services.impl.IDMAPIServiceImpl;
import com.covisint.idm.services.impl.PiServiceImpl;
@Component("piTasksService")
public class PiTasksServiceImpl {
	
	static final Logger logger = LoggerFactory.getLogger(PiTasksServiceImpl.class);
	
	@Autowired
	@Qualifier("piUtilService")
	private IDMAPIServiceImpl piUtilService;
	
	@Autowired
	@Qualifier("piService")
	private PiServiceImpl piService;
	
	
	public void getToken() {
		logger.debug("Token found:"+piUtilService.getToken());
	}
	public void createPerson(){
		Person resp = piUtilService.createPerson();
		logger.debug("Person found:"+(resp.getEmail()));
	}
	public void getPersons() {
		List<Person> resp = piUtilService.getPersonInfo("");
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
		List<Packages> pkgs =(List<Packages>)resp.getPackages();
		for (Iterator iterator = pkgs.iterator(); iterator.hasNext();) {
			Packages packages = (Packages) iterator.next();
			ServicePackage pkg =packages.getServicePackage();
			if(pkg.getId().equalsIgnoreCase("PBIT2-CLP10920176")){
				if(packages.getStatus().equalsIgnoreCase("active")){
					piService.activateService(new PiFunction(), resp,pkg );
				}else{
					piService.deactivateService(new PiFunction(), resp,pkg );
				}
			}
		}		
		logger.debug("Person found:"+(resp.toString()));
	}
}

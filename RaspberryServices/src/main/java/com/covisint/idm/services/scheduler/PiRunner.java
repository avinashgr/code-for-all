package com.covisint.idm.services.scheduler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.covisint.idm.services.impl.IDMAPIServiceImpl;

public class PiRunner {
	public static void main(String args[]) {
		ApplicationContext appContext;
		if(args.length>0 && args[0].equalsIgnoreCase("register")){
			appContext = new ClassPathXmlApplicationContext(new String[]{"app-properties.xml"});
			IDMAPIServiceImpl idmImpl = (IDMAPIServiceImpl)appContext.getBean("piUtilService");
			idmImpl.createPerson();
		}else{
			appContext = new ClassPathXmlApplicationContext(new String[]{"app-raspiservices.xml","app-properties.xml"});
		}
	}

}
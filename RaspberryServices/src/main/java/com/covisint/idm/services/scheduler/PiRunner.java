package com.covisint.idm.services.scheduler;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PiRunner {
	public static void main(String args[]) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("app-raspiservices.xml");
	}

}
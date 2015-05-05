package com.covisint.idm.services.impl;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.covisint.idm.services.entities.packagev2.ServicePackage;
import com.covisint.idm.services.entities.personv2.Person;
import com.covisint.idm.services.entities.pi.PiFunction;
import com.covisint.idm.services.entities.pi.PiInfo;
import com.covisint.idm.services.entities.twitter.TwitterResponse;
import com.covisint.idm.services.interfaces.DeviceService;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.restr.api.entity.APIResponse;

@Component
@Qualifier("piService")
public class PiServiceImpl implements DeviceService<PiInfo> {
	private static final String DEVICE_OFF_MESSAGE = ": I am OFF! Goodbye!";
	private static final String DEVICE_ON_MESSAGE = ": I am ON! Sunny and nice";
	private static final Logger logger = LoggerFactory.getLogger(PasswordPolicyServiceImpl.class);
	private static GpioController gpio = GpioFactory.getInstance();
	GpioPinDigitalOutput led = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.LOW);

	@Autowired
	@Qualifier("twitterService")
	private TwitterServiceImpl twitterService;	

	public PiInfo readPiInfo() {
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
		} catch (UnknownHostException uhe) {

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public PiInfo activateService(PiFunction service) {
		led.high();
		return null;
	}
	
	public PiInfo activateService(PiFunction service, Person p, ServicePackage sp) {
		try{
			boolean tweet=false;
			if(led.isLow()){
				tweet = true;
			}else{
				tweet = false;
			}
			twitterService.sendMessage(p, sp, tweet, p.getName().getGiven()+":"+sp.getId()+DEVICE_ON_MESSAGE);
			activateService(service);
		}catch(Exception e){
			logger.error("There was an exception",e);
		}
		return null;
	}
	
	public PiInfo deactivateService(PiFunction service, Person p, ServicePackage sp) {
		try{
			boolean tweet=false;
			if(led.isHigh()){
				tweet = true;
			}else{
				tweet = false;
			}
			twitterService.sendMessage(p, sp, tweet,  p.getName().getGiven()+":"+sp.getId()+DEVICE_OFF_MESSAGE);
			deactivateService(service);
		}catch(Exception e){
			logger.error("There was an exception",e);
		}
		return null;
	}




	public PiInfo deactivateService(PiFunction service) {
		try{
			led.low();
		}catch(Exception e){
			logger.error("There was an exception",e);
		}
		return null;
	}

	public PiInfo stopPi(PiInfo p) {
		// TODO Auto-generated method stub
		return null;
	}

	public PiInfo startPi(PiInfo p) {
		// TODO Auto-generated method stub
		return null;
	}


}

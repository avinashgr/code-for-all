package com.covisint.idm.services.impl;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.covisint.idm.services.entities.pi.PiFunction;
import com.covisint.idm.services.entities.pi.PiInfo;
import com.covisint.idm.services.interfaces.DeviceService;
@Component
@Qualifier("piService")
public class PiServiceImpl implements DeviceService<PiInfo> {

	public PiInfo readPiInfo() {
		InetAddress ip;
		try{
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();			
		}catch(UnknownHostException uhe){
			
		}catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public PiInfo activateService(PiFunction service) {
		// TODO Auto-generated method stub
		return null;
	}

	public PiInfo deactivateService(PiFunction service) {
		// TODO Auto-generated method stub
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

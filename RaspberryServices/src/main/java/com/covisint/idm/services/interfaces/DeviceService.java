package com.covisint.idm.services.interfaces;

import com.covisint.idm.services.entities.pi.PiFunction;
import com.covisint.idm.services.entities.pi.PiInfo;

/**
 * 
 * @author aranjalkar
 *
 * @param <T> - the DeviceInfo
 */
public interface DeviceService<T extends DeviceInfo> {
	abstract T readPiInfo();
	abstract T activateService(PiFunction service);
	abstract T deactivateService(PiFunction service);
	abstract T stopPi(T p);
	abstract T startPi(T p);	
}

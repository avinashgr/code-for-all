package com.covisint.idm.services.entities.pi;

import java.util.List;

import com.covisint.idm.services.interfaces.DeviceInfo;


public class PiInfo extends DeviceInfo{
	private String piID;
	private String piName;
	private String piMACAddress; 
	private List<Attribute> attributes;
	private List<PiFunction> functions;

	public List<Attribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	public List<PiFunction> getFunctions() {
		return functions;
	}
	public void setFunctions(List<PiFunction> functions) {
		this.functions = functions;
	}
	public String getPiID() {
		return piID;
	}
	public void setPiID(String piID) {
		this.piID = piID;
	}
	public String getPiName() {
		return piName;
	}
	public void setPiName(String piName) {
		this.piName = piName;
	}
	public String getPiMACAddress() {
		return piMACAddress;
	}
	public void setPiMACAddress(String piMACAddress) {
		this.piMACAddress = piMACAddress;
	}


}

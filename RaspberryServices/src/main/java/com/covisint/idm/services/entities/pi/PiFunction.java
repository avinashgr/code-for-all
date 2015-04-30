package com.covisint.idm.services.entities.pi;

/**
 * Functions available in Pi
 * 
 * @author aranjalkar
 *
 */
public class PiFunction {
	private String id;
	private String functionName;
	private String functionDesc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionDesc() {
		return functionDesc;
	}

	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}

}

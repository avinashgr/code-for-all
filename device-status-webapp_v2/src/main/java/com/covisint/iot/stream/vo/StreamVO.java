package com.covisint.iot.stream.vo;

public class StreamVO {
	private String messageId;
	private String commandId;
	private String credentials;
	private String encodingtype;
	private String message;
	private String deviceId;

	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getCommandId() {
		return commandId;
	}
	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}
	public String getCredentials() {
		return credentials;
	}
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
	public String getEncodingtype() {
		return encodingtype;
	}
	public void setEncodingtype(String encodingtype) {
		this.encodingtype = encodingtype;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
//	  .add("commandId", commandId)
//	  .add("credentials", "deviceUsername:devicePassword")
//	  .add("encodingtype", "base64")
//	  .add("message"

}

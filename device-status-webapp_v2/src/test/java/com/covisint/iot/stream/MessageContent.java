package com.covisint.iot.stream;
/**
 * Created for the Message contents in the IOT platform
 * @author aranjalkar
 *
 */
public class MessageContent {
    private String messageId;
    private String deviceId;
    private String commandId;
    private String message;
    private String encodingType;
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getCommandId() {
		return commandId;
	}
	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEncodingType() {
		return encodingType;
	}
	public void setEncodingType(String encodingType) {
		this.encodingType = encodingType;
	}


}

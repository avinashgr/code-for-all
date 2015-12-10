package com.covisint.css.portal;
/**
 * Java POJO Mapping for the HttpRequest params
 * @author aranjalkar
 *
 */
public class DeviceMessage {

    private String message;
    private String deviceId;
    private String command;
    private String publishToTopic;
    private String subscribeToTopic;
    private String appId;
    
    public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message=message;
    }
	public String getPublishToTopic() {
		return publishToTopic;
	}
	public void setPublishToTopic(String publishToTopic) {
		this.publishToTopic = publishToTopic;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSubscribeToTopic() {
		return subscribeToTopic;
	}
	public void setSubscribeToTopic(String subscribeToTopic) {
		this.subscribeToTopic = subscribeToTopic;
	}
	@Override
	public String toString() {
		return "DeviceMessage [message=" + message + ", deviceId=" + deviceId + ", command=" + command
				+ ", publishToTopic=" + publishToTopic + ", subscribeToTopic=" + subscribeToTopic + ", appId=" + appId
				+ "]";
	}
	
}

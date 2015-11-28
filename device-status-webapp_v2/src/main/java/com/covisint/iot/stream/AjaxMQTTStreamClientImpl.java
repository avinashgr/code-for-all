
package com.covisint.iot.stream;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.covisint.css.portal.DeviceResponse;
import com.covisint.css.portal.DeviceMessage;
import com.covisint.css.portal.utils.ExposablePropertyPlaceholderConfigurer;



@Component("ajaxmqttservice")
public class AjaxMQTTStreamClientImpl implements MqttCallback, StreamTasks{
	final static Logger logger = LoggerFactory.getLogger(AjaxMQTTStreamClientImpl.class);
	// the MQTT client for Connecting to Covisint IoT Platform

	private final Map<String, MqttClient> clients =  new HashMap<String, MqttClient>();
	
    @Qualifier("propertyConfigurer")
    @Autowired
    private ExposablePropertyPlaceholderConfigurer propertyConfigurer;
    

	//stores all the messages
	private final Map<String, LinkedBlockingQueue<DeviceResponse>> messages = new HashMap<String,LinkedBlockingQueue<DeviceResponse>>();	

    
    public Queue<DeviceResponse> getMessages(String topic) {
		return messages.get(topic);
	}

	/* (non-Javadoc)
	 * @see com.covisint.iot.stream.StreamTasks#initializeMQTTConnection(java.lang.String, java.lang.String)
	 */
    public void initializeMQTTConnection(String topic, String appId)
    {
    	if(null==clients.get(appId) || !clients.get(appId).isConnected()){
    		MqttClient mqttClient =connectClient(topic,appId);
    		if(null!=mqttClient && mqttClient.isConnected()){
	    		clients.put(appId,mqttClient);
	    		String alertConsumerTopic = propertyConfigurer.getProperty(appId+".mqtt.alertConsumerTopic");
	    		subscribeAndInitializeTopic(alertConsumerTopic, appId);
    		}else{
    			clients.remove(appId);
    			logger.info("Unable to connect to client for topic:"+topic+" for appId :"+appId);
    		}
    	}
    	
    }



	public void subscribeAndInitializeTopic(String topic, String appId) {
		try {
			clients.get(appId).subscribe(topic);
			if(null==messages.get(topic)){
				messages.put(topic, new LinkedBlockingQueue<DeviceResponse>());				
			}
			logger.info("Subcribed to topic successfully:"+topic);
		} catch (MqttException e) {
			logger.info("Subscribe failed for topic:"+topic,e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.covisint.iot.stream.StreamTasks#subscribeAndInitializeTopic(com.covisint.css.portal.DeviceMessage)
	 */
	public void subscribeAndInitializeTopic(DeviceMessage deviceMessage) {
		String topic = deviceMessage.getSubscribeToTopic();
		try {
			clients.get(deviceMessage.getAppId()).subscribe(deviceMessage.getSubscribeToTopic());
			logger.info("Subcribed to topic successfully:"+topic);
		} catch (MqttException e) {
			logger.info("Subscribe failed for topic:"+topic,e);
		}
	}

	private MqttClient connectClient(String topic,String appId) {
		MqttClient client;
		/**
    	 * Stream connectivity information. All information provided by Covisint Iot Stream service at the 
    	 * time of stream creation for this application.
    	 * ALL OF THESE VARIABLES SHOULD BE REPLACED WITH THE VALUES RETURNED WHEN CREATING YOUR COVISINT APPLICATION STREAM  
    	 */
		//
		String protocol = propertyConfigurer.getProperty(appId+".mqtt.protocol");
    	// the host for the Covisint IoT MQTT broker
    	String connectionHost = propertyConfigurer.getProperty(appId+".mqtt.connectionHost");
    	// the port of the Covisint IoT MQTT broker
    	String connectionPort = propertyConfigurer.getProperty(appId+".mqtt.connectionPort");
    	// the username required for connecting to the Covisint IoT MQTT broker
    	String protocolSecurityUsername = propertyConfigurer.getProperty(appId+".mqtt.protocolSecurityUsername");
    	// the password required for connecting to the Covisint IoT MQTT broker
    	String protocolSecurityPassword = propertyConfigurer.getProperty(appId+".mqtt.protocolSecurityPassword");
    	// the clientID required for connecting to the Covisint IoT MQTT broker
    	String protocolSecurityClientId = propertyConfigurer.getProperty(appId+".mqtt.protocolSecurityClientId");
    	try
    	{  
	      client = new MqttClient(protocol+"://" + connectionHost + ":" + connectionPort , protocolSecurityClientId + new Random().nextInt(),null);
	      MqttConnectOptions connectOptions =  new MqttConnectOptions();
	      connectOptions.setUserName(protocolSecurityUsername);
	      connectOptions.setPassword(protocolSecurityPassword.toCharArray());

	      connectOptions.setConnectionTimeout(Integer.parseInt(propertyConfigurer.getProperty(appId+".mqtt.connectionTimeOut")));
	      connectOptions.setKeepAliveInterval(Integer.parseInt(propertyConfigurer.getProperty(appId+".mqtt.connectionKeepAlive")));
	      connectOptions.setCleanSession(true);
	      client.connect(connectOptions);
	      logger.info("client connected for appid: "+appId);
	      client.setCallback(this);
	      return client;
    	} catch(Exception ex) {
    		ex.printStackTrace();
    		return null;
    	}

	}
    
    /* (non-Javadoc)
	 * @see com.covisint.iot.stream.StreamTasks#messageArrived(java.lang.String, org.eclipse.paho.client.mqttv3.MqttMessage)
	 */
	public void messageArrived(String topic, MqttMessage message)
			throws Exception {
		try{
			String messageRecieved =new String(message.getPayload());
			if(null==messages.get(topic)){
				messages.put(topic, new LinkedBlockingQueue<DeviceResponse>());
			}
			if (messages.get(topic).size() > 0) {
				messages.get(topic).remove();
			}
			DeviceResponse deviceMessage = new DeviceResponse(messageRecieved);
			messages.get(topic).add(deviceMessage);
		} catch (Exception e) {
			logger.error("Exception thrown",e);
		}

	}
	  
	  /* (non-Javadoc)
	 * @see com.covisint.iot.stream.StreamTasks#publishCommand(com.covisint.css.portal.DeviceMessage, java.lang.String)
	 */
	  public void publishCommand(DeviceMessage info, String appId)
	  {
	    try {    	  
	      byte[] messageBytes = createStreamContent(info.getMessage(), info.getCommand());
	      MqttMessage message = new MqttMessage();
	      message.setPayload(messageBytes);
	      	// publish command to producer topic
		  clients.get(appId).publish(info.getPublishToTopic(), message);
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	  }

	private byte[] createStreamContent(String message, String commandId) {
//		StreamVO streamVO =  new StreamVO();
//	      streamVO.setMessageId(deviceId);
//	      streamVO.setMessage(commandId);
//	      streamVO.setCredentials(deviceId+":"+commandId);
	    byte[] messageBytes = message.getBytes();
		return messageBytes;
	}
	  
	  /**
	   * Based on the definition for the Application Stream.
	   * Encrypt the commandArgs using the producerPrivateKey and then encode.
	   * 
	   * @param commandArgs
	   * @return
	   */
	  private String encryptAndEncodeCommandArgs(String commandArgs)
	  {
		  return commandArgs;
	  }
	  
	  /* (non-Javadoc)
	 * @see com.covisint.iot.stream.StreamTasks#connectionLost(java.lang.Throwable)
	 */
	  public void connectionLost (Throwable cause) {}
	  
	  
	  /* (non-Javadoc)
	 * @see com.covisint.iot.stream.StreamTasks#deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken)
	 */
	public void deliveryComplete(IMqttDeliveryToken token) {
		  logger.info("Message delivered:"+token);
	  }
	  
	  private String findMessage(String regex, String expressionToParse){
		  Pattern pattern = Pattern.compile("\"(.*?)\"");
		  Matcher matcher = pattern.matcher(expressionToParse);
		  if (matcher.find())
		  {
		      return matcher.group(1);
		  }else{
			  return "not found";
		  }

	  
	  }
	  
	  /* (non-Javadoc)
	 * @see com.covisint.iot.stream.StreamTasks#stopSubscribe(java.lang.String, java.lang.String)
	 */
	public void stopSubscribe(String topic, String appId){
		  if(clients.get(appId).isConnected()){
			messages.get(topic).remove();
//				client.disconnect();
		   }
	  }
	  
}
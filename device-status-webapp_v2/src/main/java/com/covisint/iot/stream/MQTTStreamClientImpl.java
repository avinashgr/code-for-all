
package com.covisint.iot.stream;
import java.util.HashMap;
import java.util.Map;
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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.covisint.css.portal.DeviceMessage;
import com.covisint.css.portal.DeviceResponse;
import com.covisint.css.portal.utils.ExposablePropertyPlaceholderConfigurer;
import com.covisint.css.portal.utils.StreamConfigUtil;
import com.covisint.iot.stream.vo.StreamVO;
import com.google.gson.Gson;


/**
 * Service for the STOMP based 
 * @author aranjalkar
 *
 */
@Component("mqttservice")
public class MQTTStreamClientImpl implements MqttCallback,StreamTasks {
	final static Logger logger = LoggerFactory.getLogger(MQTTStreamClientImpl.class);
	private final Map<String, MqttClient> clients =  new HashMap<String, MqttClient>();
	@Qualifier("propertyConfigurer")
	@Autowired
    private ExposablePropertyPlaceholderConfigurer propertyConfigurer;
    @Qualifier("streamconfigurator")
    @Autowired
    private StreamConfigUtil streamConfigurator;
	
    private final SimpMessagingTemplate messagingTemplate;
	/**
	 * Singleton for this object. 
	 * @return
	 */
    @Autowired
    public MQTTStreamClientImpl(SimpMessagingTemplate messagingTemplate) {
    	this.messagingTemplate = messagingTemplate;
    }
    
	public boolean initializeMQTTConnection(String topic, String appId) {
		if(streamConfigurator.getStreamForStreamId(appId) != null){
	    	if(null==clients.get(appId) || !clients.get(appId).isConnected()){
	    		MqttClient mqttClient =connectClient(topic,appId);
	    		if(null!=mqttClient && mqttClient.isConnected()){
		    		clients.put(appId,mqttClient);
		    		String alertConsumerTopic = streamConfigurator.getStreamForStreamId(appId).alertConsumerTopic;
		    		if(null!=alertConsumerTopic){
			    		 subscribeAndInitializeTopic(alertConsumerTopic, appId);
			    	}
		    		return true;
	    		}else{
	    			clients.remove(appId);
	    			logger.info("Unable to connect to client for topic:"+topic+" for appId :"+appId);
	    		}
	    	}
		}else{
			//if client found remove
			clients.remove(appId);
			logger.error("Configuration for topic:"+topic+" for appId :"+appId+" not found!");
		}
		return false;
	}

	public boolean subscribeAndInitializeTopic(String topic, String appId) {
		boolean subscribeSuccessful=false;
		if(null!=clients.get(appId)){
			try {
				clients.get(appId).subscribe(topic);
				logger.info("Subcribed to topic successfully:"+topic);
			} catch (MqttException e) {
				logger.info("Subscribe failed for topic:"+topic,e);
			}	
		}
		return subscribeSuccessful;
	}

	public void publishCommand(DeviceMessage info, String appId) {
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
	    byte[] messageBytes = message.getBytes();
		return messageBytes;
	}

	public void stopSubscribe(String topic, String appId) {
		// TODO Auto-generated method stub
		
	}

	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}

	public void messageArrived(String topic, MqttMessage arg1) throws Exception {
		try{
			String messageRecieved =new String(arg1.getPayload());
			DeviceResponse deviceMessage = new DeviceResponse(messageRecieved);
			messagingTemplate.convertAndSend("/topic/"+topic, deviceMessage);
		} catch (Exception e) {
			logger.error("Exception thrown",e);
		}

	}
	/**
	 * creates an mqtt client for the topic and app id passed
	 * if app id is configured in the properties
	 * @param topic
	 * @param appId
	 * @return
	 */
	private MqttClient connectClient(String topic,String appId) {
		String protocol = propertyConfigurer.getProperty(appId+".mqtt.protocol", "ssl");
//		String protocol = streamConfigurator.getStreamForStreamId(appId).protocolType;
		if(null!=streamConfigurator.getStreamForStreamId(appId)){
    	 return configureClientForApp(appId, protocol);
		}else{
			return null;
		}

	}

	private MqttClient configureClientForApp(String appId, String protocol) {
		logger.info(String.format("Creating the mqtt client for:%s and protocol:%s",appId,protocol));
		/**
    	 * Stream connectivity information. All information provided by Covisint Iot Stream service at the 
    	 * time of stream creation for this application.
    	 * ALL OF THESE VARIABLES SHOULD BE REPLACED WITH THE VALUES RETURNED WHEN CREATING YOUR COVISINT APPLICATION STREAM  
    	 */
		//
		MqttClient client;
		// the host for the Covisint IoT MQTT broker
    	String connectionHost = streamConfigurator.getStreamForStreamId(appId,"host");
    	// the port of the Covisint IoT MQTT broker
    	String connectionPort = streamConfigurator.getStreamForStreamId(appId,"port");
    	// the username required for connecting to the Covisint IoT MQTT broker
    	String protocolSecurityUsername = streamConfigurator.getStreamForStreamId(appId,"username");
    	// the password required for connecting to the Covisint IoT MQTT broker
    	String protocolSecurityPassword = streamConfigurator.getStreamForStreamId(appId,"password");
    	// the clientID required for connecting to the Covisint IoT MQTT broker
    	String protocolSecurityClientId = streamConfigurator.getStreamForStreamId(appId,"clientId");
    	try
    	{  
    	  
	      client = new MqttClient(protocol+"://" + connectionHost + ":" + connectionPort , protocolSecurityClientId + new Random().nextInt(),null);
	      MqttConnectOptions connectOptions =  new MqttConnectOptions();
	      connectOptions.setUserName(protocolSecurityUsername);
	      connectOptions.setPassword(protocolSecurityPassword.toCharArray());

	      connectOptions.setConnectionTimeout(Integer.parseInt(propertyConfigurer.getProperty(appId+".mqtt.connectionTimeOut","50")));
	      connectOptions.setKeepAliveInterval(Integer.parseInt(propertyConfigurer.getProperty(appId+".mqtt.connectionKeepAlive","100")));
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
	  
}
package com.covisint.iot.stream;

import java.util.Base64;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.covisint.css.portal.DeviceMessage;
import com.covisint.iot.stream.test.RandomizeUtils;
import com.google.gson.GsonBuilder;


public class TestPublishCoke {
  public static void main(String[] args) {
	  ClassPathXmlApplicationContext cp= new ClassPathXmlApplicationContext("application-config.xml");
	  MQTTStreamClientImpl sh = (MQTTStreamClientImpl) cp.getBean("mqttservice");
	  try{
		  for(int i=0;i<=1000;i++){
			  System.out.println("Message: "+i);
			  sendSampleMessage(sh); 
			  Thread.sleep(3000);
		  }
	  }catch(InterruptedException ex){
		  System.out.println("Interrupted");
	  }
  }

private static void sendSampleMessage(MQTTStreamClientImpl sh) {
	DeviceMessage deviceMessage = new DeviceMessage();

	  MessageContent content = new MessageContent();
	  content.setCommandId("3bff3edd-2068-4f89-81bd-d5f6f80b9569");
	  content.setDeviceId("051f8640-7afa-42a5-b842-868ed1a0adc2");
	  content.setEventTemplateId("1a80f0d5-42c6-4398-bf89-2a8ed44deced");
	  content.setMessage(createSampleMessage());
	  content.setEncodingType("base64");
	  
	  System.out.println("Message format:"+new GsonBuilder().setPrettyPrinting().create().toJson(content));

	  deviceMessage.setPublishToTopic("7348d5cf-e1a5-45c6-8ab9-5e379746a109");
	  deviceMessage.setMessage((new GsonBuilder().disableHtmlEscaping().create()).toJson(content));
	  deviceMessage.setAppId("df0f11e1-6aaa-4104-82b1-65104cc8ce1b");
	  sh.initializeMQTTConnection(deviceMessage.getPublishToTopic(), deviceMessage.getAppId());
	  sh.publishCommand(deviceMessage,deviceMessage.getAppId());
}

private static String createSampleMessage() {
	
	String message = "{"+"\""+"cuid"+"\":"+"Raghu:"+RandomizeUtils.randomInt(30,35)+","+"\""+"password\""+":"+"Avinash:"+RandomizeUtils.randomInt(40,60)+"}";
	System.out.println("message sent:"+message);
	String decodedBytes =Base64.getEncoder().encodeToString( message.getBytes());
	return decodedBytes;
}

}

package com.covisint.iot.stream;

import java.util.Base64;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import com.covisint.css.portal.DeviceMessage;
import com.covisint.css.portal.utils.StreamConfigUtil;
import com.covisint.iot.stream.test.RandomizeUtils;
import com.google.gson.GsonBuilder;


public class TestPublishCDemo {
  public static void main(String[] args) {
	  ClassPathXmlApplicationContext cp= new ClassPathXmlApplicationContext("application-config.xml");
	  new StreamConfigUtil().setPath((ClassLoader.getSystemResource("streaminfo.json")).getPath());
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
	  content.setCommandId("CommandTSSequenceNum1100112");
	  content.setDeviceId("7e57a97c-4e1a-49c6-a2b1-6304507c633e");
	  content.setCommandId("5a59525e-106d-4ac0-9f0c-1dffe8b7a3cb");
	  content.setEventTemplateId("c8f56516-0494-4574-8511-27a1929123e9");
	  content.setMessage(createSampleMessage());
	  content.setEncodingType("base64");
	  
	  System.out.println("Message format:"+new GsonBuilder().setPrettyPrinting().create().toJson(content));

	  deviceMessage.setPublishToTopic("98a60468-2091-4929-8b0e-5e401f00711b");
	  deviceMessage.setMessage((new GsonBuilder().disableHtmlEscaping().create()).toJson(content));
	  deviceMessage.setAppId("fc17295f-a11a-4a36-8ff2-6d23ba7b31d4");
	  sh.initializeMQTTConnection(deviceMessage.getPublishToTopic(), deviceMessage.getAppId());
	  sh.publishCommand(deviceMessage,deviceMessage.getAppId());
}

private static String createSampleMessage() {
	
	String message = "{"+"\""+"temp"+"\":"+RandomizeUtils.randomInt(30,35)+","+"\""+"humidity\""+":"+RandomizeUtils.randomInt(40,60)+"}";
	System.out.println("message sent:"+message);
	String decodedBytes =Base64.getEncoder().encodeToString( message.getBytes());
	return decodedBytes;
}

}

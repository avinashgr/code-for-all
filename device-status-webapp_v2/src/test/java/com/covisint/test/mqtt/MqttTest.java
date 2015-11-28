package com.covisint.test.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttTest implements MqttCallback {

    private MqttClient client;
    
    public static void main(String[] args) {
    	new MqttTest();
	}

    public MqttTest() {
        String url = "ssl://mqtt.covapp.io:8883";
        String clientID = "958150eb27ac4f42a845";
        try {
            client = new MqttClient(url, clientID);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName("85110bdb-3d61-43f3-ae4f-ea6bc013f88b");
            options.setPassword("c43f7bb6-2887-4942-9862-624edff2fde6".toCharArray());
            options.setCleanSession(true);
            client.connect(options);
            
            client.subscribe("327af13d-1133-46cf-83e8-60dfefafe641");            
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    public void connectionLost(Throwable cause) {
        // TODO Auto-generated method stub

    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Message arrived, biatch!");

    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub

    }

}
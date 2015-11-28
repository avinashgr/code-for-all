package com.covisint.iot.stream;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.covisint.css.portal.DeviceMessage;

public interface StreamTasks {

	/** 
	 * Initialize the MQTT client. Connect to the MQTT broker and subscribe to consumerTopic and alertConsumerTopic
	 * 
	 */
	void initializeMQTTConnection(String topic, String appId);

	void subscribeAndInitializeTopic(String topic, String appId);

	/**
	 * Callback function when a Device event message is retrieved from subscribed topic. consumerTopic and alertConsumerTopic.
	 * The message.getPayload will be in the format of a SendEvent message.
	 * Read the SendEvent.encodingType of the message to determine if you should decode the SendEvent.message object. Can be base64 or none.
	 * Use the consumerPublicKey to decrypt the message. Only decrypt if this Application Stream 
	 * is configured to encrypt message.
	 * 
	 */
	void messageArrived(String topic, MqttMessage message) throws Exception;

	/**
	   * Construct a SendCommand message that will be published to the producerTopic. This message will ultimately 
	   * be delivered to the target device to process.
	   * 
	   * @param deviceId
	   * @param commandId
	   * @param commandArgs
	   */
	void publishCommand(DeviceMessage info, String appId);

	/**
	   * try to reconnect to the MQTT Broker with connectivity info and credentials  
	   */
	void connectionLost(Throwable cause);

	void deliveryComplete(IMqttDeliveryToken token);

	void stopSubscribe(String topic, String appId);

}
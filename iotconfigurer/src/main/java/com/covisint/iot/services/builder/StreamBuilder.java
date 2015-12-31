package com.covisint.iot.services.builder;

import com.covisint.iot.services.vo.AttributeType;

/**
 * Builds a stream
 * @author aranjalkar
 *
 */
public class StreamBuilder {
 
 private final AttributeType attributeType;
 public static class Builder{
	 //required parameters
     private final String clientId;
     private final String clientSecret;
     //optional parameters
     private AttributeType attributeType;
     public Builder(String clientId, String clientSecret){
    	 this.clientId = clientId;
    	 this.clientSecret = clientSecret;
     }
     public Builder addAttributeType(AttributeType attr){
    	 this.attributeType=attr;
    	 return this;
     }
     public StreamBuilder build() {
         return new StreamBuilder(this);
     }
 }

 private StreamBuilder(Builder builder) {
	 attributeType = builder.attributeType;
 }
	
}

package com.covisint.iot.stream.vo;
/**
 * Created for the stream reading
 * @author aranjalkar
 *
 */
public final class StreamInfoVO {
    public final String id;
    public final String version;
    public final String creator;
    public final String creatorAppId;
    public final long creation;
    public final String realm;
    public final Name name[];
    public final String streamType;
    public final String protocolType;
    public final String protocolSecurityType;
    public final String payloadSecurityType;
    public final String ownerId;
    public final String consumerTopic;
    public final String alertConsumerTopic;
    public final String producerTopic;
    public final String messageProcessorPartitionId;
    public final StreamConfiguration streamConfiguration;
    public final ProtocolSecurityAttribute protocolSecurityAttributes[];

    public StreamInfoVO(String id, String version, String creator, String creatorAppId, long creation, String realm, Name[] name, String streamType, String protocolType, String protocolSecurityType, String payloadSecurityType, String ownerId, String consumerTopic, String alertConsumerTopic,String producerTopic, String messageProcessorPartitionId, StreamConfiguration streamConfiguration, ProtocolSecurityAttribute[] protocolSecurityAttributes){
        this.id = id;
        this.version = version;
        this.creator = creator;
        this.creatorAppId = creatorAppId;
        this.creation = creation;
        this.realm = realm;
        this.name = name;
        this.streamType = streamType;
        this.protocolType = protocolType;
        this.protocolSecurityType = protocolSecurityType;
        this.payloadSecurityType = payloadSecurityType;
        this.ownerId = ownerId;
        this.consumerTopic = consumerTopic;
        this.alertConsumerTopic = alertConsumerTopic;
        this.producerTopic = producerTopic;
        this.messageProcessorPartitionId = messageProcessorPartitionId;
        this.streamConfiguration = streamConfiguration;
        this.protocolSecurityAttributes = protocolSecurityAttributes;
    }

    public static final class Name {
        public final String lang;
        public final String text;

        public Name(String lang, String text){
            this.lang = lang;
            this.text = text;
        }
    }

    public static final class StreamConfiguration {
        public final String logMode;
        public final long pullingThreads;
        public final long sleepTime;
        public final long quota;

        public StreamConfiguration(String logMode, long pullingThreads, long sleepTime, long quota){
            this.logMode = logMode;
            this.pullingThreads = pullingThreads;
            this.sleepTime = sleepTime;
            this.quota = quota;
        }
    }

    public static final class ProtocolSecurityAttribute {
        public final String name;
        public final String value;

        public ProtocolSecurityAttribute(String name, String value){
            this.name = name;
            this.value = value;
        }
    }
}
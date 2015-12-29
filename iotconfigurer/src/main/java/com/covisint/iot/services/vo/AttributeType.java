package com.covisint.iot.services.vo;

public final class AttributeType {
    public final String creator;
    public final String realm;
    public final String creatorAppId;
    public final String name;
    public final Description description[];
    public final String type;
    public final boolean isActive;
    public final boolean isFrozen;

    public AttributeType(String creator, String realm, String creatorAppId, String name, Description[] description, String type, boolean isActive, boolean isFrozen){
        this.creator = creator;
        this.realm = realm;
        this.creatorAppId = creatorAppId;
        this.name = name;
        this.description = description;
        this.type = type;
        this.isActive = isActive;
        this.isFrozen = isFrozen;
    }

    public static final class Description {
        public final String lang;
        public final String text;

        public Description(String lang, String text){
            this.lang = lang;
            this.text = text;
        }
    }
}

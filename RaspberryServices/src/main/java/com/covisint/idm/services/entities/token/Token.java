package com.covisint.idm.services.entities.token;

public class Token {
	private String creator;
	private String creatorAppId;
	private String creation;
	private String access_token;
	private String expires_in;
	private String token_type;
	private String expirationTime;
	private String issueTime;
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreatorAppId() {
		return creatorAppId;
	}
	public void setCreatorAppId(String creatorAppId) {
		this.creatorAppId = creatorAppId;
	}
	public String getCreation() {
		return creation;
	}
	public void setCreation(String creation) {
		this.creation = creation;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}
	public String getIssueTime() {
		return issueTime;
	}
	public void setIssueTime(String issueTime) {
		this.issueTime = issueTime;
	}
	@Override
	public String toString() {
		return "Token [creator=" + creator + ", creatorAppId=" + creatorAppId
				+ ", creation=" + creation + ", access_token=" + access_token
				+ ", expires_in=" + expires_in + ", token_type=" + token_type
				+ ", expirationTime=" + expirationTime + ", issueTime="
				+ issueTime + "]";
	}
	
}

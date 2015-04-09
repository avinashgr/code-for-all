package com.covisint.cisco.api.entity;

public class ProxyInfo {
	private String proxyDNS;
	private int proxyPort;
	public int getProxyPort() {
		return proxyPort;
	}
	public ProxyInfo setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
		return this;
	}
	public String getProxyDNS() {
		return proxyDNS;
	}
	public ProxyInfo setProxyDNS(String proxyDNS) {
		this.proxyDNS = proxyDNS;
		return this;
	}
}

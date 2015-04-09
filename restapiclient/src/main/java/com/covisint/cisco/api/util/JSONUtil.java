package com.covisint.cisco.api.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.covisint.cisco.api.entity.APIResponse;
import com.covisint.cisco.api.entity.User;
import com.covisint.cisco.api.entity.APIResponse.HttpMethod;
import com.covisint.cisco.api.entity.ProxyInfo;
import com.google.gson.Gson;

public class JSONUtil {

	public static final ProxyInfo proxy = new ProxyInfo().setProxyDNS(
			"10.66.1.108").setProxyPort(3128);

	public Map getApplications(String content) {
		Gson gson = new Gson();
		Map appData = gson.fromJson(content, Map.class);
		return appData;
	}

	public String getSampleJSON(String content) {
		Gson gson = new Gson();
		User user = new User();
		user.setMiddleName("Tinny");
		user.setIdpUserID("12344");
		user.setAddress1("Birchgrove Dr, Apt 007");
		user.setAddress2("Grove Bend");
		user.setCity("Sterling Heights");
		return gson.toJson(user);
	}

	public String getTokenJSON(String content) {
		List<String>[] params = new ArrayList[4];
		initializeTokenParams(params);

		APIResponse response = new APIResponse();
		Map<String, String> formInfo = APIUtil.setRequestParams(params,
				response);
		response.setRequestParams(formInfo);
		response.setRequestMethod(HttpMethod.POST);
		setProxy(response);
		APIUtil.processRequest(response);
		System.out.println("Response:" + response.getResponseContent());
		return response.getResponseContent();
	}

	public String getUserJSON(String token, String userName) {
		List<String>[] params = initializeUserParams(token, userName);
		APIResponse response = new APIResponse();
		Map<String, String> formInfo = APIUtil.setRequestParams(params,response);
		response.setRequestParams(formInfo);
		response.setRequestMethod(HttpMethod.GET);
		setProxy(response);
		APIUtil.processRequest(response);
		System.out.println("Response:" + response.getResponseContent());
		return response.getResponseContent();
	}

	private List<String>[]  initializeUserParams(String token, String userName) {
		List<String>[] params = new ArrayList[4];
		params[0] = new ArrayList<String>();
		params[0].add("access_token:"+token);
		params[0].add("client_id:8c241ea412e00ef219b82a6a4e5fd9b0");
		params[1] = new ArrayList<String>();
		params[1].add("idpUserID:"+userName);
		params[2] = new ArrayList<String>();
		params[2].add("https://api2.stg.covisint.com/cisco/cis/virtualconnect/v1/Users");
		return params;
	}

	/**
	 * Set Proxy if required
	 * 
	 * @param response
	 */
	private void setProxy(APIResponse response) {
		response.setHttpProxy(proxy);
		response.setHttpsProxy(proxy);
	}

	private void initializeTokenParams(List<String>[] params) {
		params[0] = new ArrayList<String>();
		params[0].add("Content-Type:application/x-www-form-urlencoded");
		params[0].add("client_id:8c241ea412e00ef219b82a6a4e5fd9b0");
		params[0].add("client_secret:3aac403787f1f2f2");
		params[1] = new ArrayList<String>();
		params[1].add("username:VIRTUALCONNECTSXP_ADMIN");
		params[1].add("grant_type:password");
		params[1].add("password:letmein2");
		params[2] = new ArrayList<String>();
		params[2]
				.add("https://api2.stg.covisint.com/cisco/cis/virtualconnect/v1/oauth2/token");
	}
	
	public static <T> T unmarshalResponse(String content, T t){
		 Gson gson = new Gson();
		 T token = (T) gson.fromJson(content, t.getClass());
		 return token;
	}
}

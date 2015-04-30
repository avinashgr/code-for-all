package com.covisint.idm.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.covisint.idm.services.entities.personv2.Addresses;
import com.covisint.idm.services.entities.personv2.Name;
import com.covisint.idm.services.entities.personv2.Organization;
import com.covisint.idm.services.entities.personv2.Person;
import com.covisint.idm.services.entities.personv2.Phones;
import com.covisint.idm.services.entities.personv2.passwordv1.AuthenticationPolicy;
import com.covisint.idm.services.entities.personv2.passwordv1.Password;
import com.covisint.idm.services.entities.personv2.passwordv1.PasswordPolicy;
import com.covisint.idm.services.entities.token.Token;
import com.google.gson.Gson;
import com.restr.api.entity.APIResponse;
import com.restr.api.util.APIUtil;

/**
 * Implements all the services that poll for IDM events
 * @author aranjalkar
 *
 */
@Component("piUtilService")

public class IDMAPIServiceImpl {
	@Value( "${idm.surname}" )
	private String IDM_SURNAME;
	@Value( "${idm.givenname}" )
	private  String IDM_GIVEN_NAME;
	@Value( "${idm.orgid}" )
	private  String IDM_ORG_ID;
	@Value( "${idm.client.password}" )
	private  String IDM_CLIENT_PASSWORD;
	@Value( "${idm.client.id}" )
	private  String IDM_CLIENT_ID;
	@Value( "${idm.password.policy.type}" )
	private  String PASSWORD_POLICY_TYPE;
	@Value( "${idm.password.policy.id}" )
	private  String PASSWORD_POLICY_ID;
	@Value( "${idm.auth.policy.id}" )
	private  String AUTHENTICATION_POLICY_ID;
	@Value( "${idm.password.is.locked}" )
	private  boolean IS_LOCKED = false;
	@Value( "${idm.auth.policy.type}" )
	private  String AUTHENTICATION_POLICY_TYPE;
	@Value( "${idm.device.expiration}" )
	private  long DEVICE_EXPIRATION;
	@Value( "${idm.device.password}" )
	private  String DEVICE_PASSWORD;
	@Value( "${idm.device.idpname}" )
	private  String DEVICE_NAME;
	@Value( "${idm.token.service.url}" )
	private  String TOKEN_SERVICE_URL;
	@Value( "${idm.person.package.service.url}" )
	private  String PERSON_PACKAGE_SERVICE_URL;
	@Value( "${idm.persons.service.url}" )
	private  String PERSONS_SERVICE_URL;
	@Value( "${idm.person.password.account.service.url}" )
	private  String PERSON_PASSWORD_ACCOUNT_SERVICE_URL;
	@Value( "${idm.device.id}" )
	private  String PERSON_ID;
	private static final Logger logger = LoggerFactory.getLogger(IDMAPIServiceImpl.class);
	@Autowired
	@Qualifier("tokenService")
	private TokenServiceImpl tokenService;
	@Autowired
	@Qualifier("personService")
	private PersonServiceImpl personService;
	@Autowired
	@Qualifier("piService")
	private PiServiceImpl piService;
	@Autowired
	@Qualifier("packageService")
	private PackageServiceImpl packageService;
	@Autowired
	@Qualifier("passwordPolicyService")
	private PasswordPolicyServiceImpl passwordPolicyService;

	
	public String getToken(){
		logger.info("Starting getToken");
		APIResponse resp = initializeTokenParams();
		Token t= tokenService.initHeaderParams(resp).read( new Token(),resp);
		logger.info("End getToken");
		return t.getAccess_token();
	}
	/*
	 * checks for services that need to be enabled
	 */
	public Person createPerson(){
		APIResponse resp = initializeTokenParams();
		Token t= tokenService.initHeaderParams(resp).read(new Token(),resp);
		Person p = createIDMPerson(t);
		createPassword(p, t);
		return p;
	}
	
	/*
	 * checks for services that need to be enabled
	 */
	public Person updatePersonPassword(){
		APIResponse resp = initializeTokenParams();
		Token t= tokenService.initHeaderParams(resp).read(new Token(),resp);
		Person p = getPersonInfoWithID();
		createPassword(p, t);
		return p;
	}
	/**
	 * Creates an "UNACTIVATED" IDM person 
	 * @param t
	 * @return
	 */
	private Person createIDMPerson(Token t) {
		APIResponse resp = initializeCreatePersonParams(t.getAccess_token());		
		Person p= personService.initHeaderParams(resp).create(new Person(), resp);
		return p;
	}
	private void createPassword(Person p, Token t) {
		APIResponse resp = initializeCreatePasswordParams(t.getAccess_token(),p);
		Password pwd= passwordPolicyService.initHeaderParams(resp).update(new Password(), resp);
	}
	private APIResponse initializeCreatePasswordParams(String access_token,Person p) {
		APIResponse response  =new APIResponse();
		List<String> params = new ArrayList<String>();
		params.add("Authorization:"+"Bearer "+access_token);
		response.setHeaderParams(APIUtil.addSplitParams(params));
		response.setJsonBody(createNewPasswordPolicy(p));
		
		response.setApiUrl(String.format(PERSON_PASSWORD_ACCOUNT_SERVICE_URL,p.getId()));
		return response;
	}

	public List<Person> getPersonInfo(){
		Token t = new Token();
		APIResponse resp = initializeTokenParams();
		t= tokenService.initHeaderParams(resp).read(t,resp);
		resp = initializePersonSearchParams(t.getAccess_token());		
		List<Person> p= personService.initHeaderParams(resp).search(new Person(), resp);
		return p;
	}
	public Person getPersonInfoWithID(){
		Token t = new Token();
		APIResponse resp = initializeTokenParams();
		t= tokenService.initHeaderParams(resp).read(t,resp);
		Person p = new Person();
		p.setId(PERSON_ID);
		resp = initializePersonInfoParams(t.getAccess_token(),p);		
		p= personService.initHeaderParams(resp).read(p, resp);
		return p;
	}
	public Person getPersonServices(){
		Token t = new Token();
		APIResponse resp = initializeTokenParams();
		t= tokenService.initHeaderParams(resp).read(t,resp);
		Person p = new Person();
		p.setId(PERSON_ID);
		resp = initializePersonInfoParams(t.getAccess_token(),p);		
		p= personService.initHeaderParams(resp).read(new Person(), resp);
		resp = initializePersonServicesParams(t.getAccess_token(),p);
		return p;
	}
	
	private APIResponse initializePersonSearchParams(String token) {
		APIResponse response  =new APIResponse();
		List<String> params = new ArrayList<String>();
		params.add("Authorization:"+"Bearer "+token);
		response.setHeaderParams(APIUtil.addSplitParams(params));
		params = new ArrayList<String>();
		params.add("username:"+DEVICE_NAME);
/*		params.add("page:2");
		params.add("pageSize:2");*/
		response.setRequestParams(APIUtil.addSplitParams(params));
		response.setApiUrl(PERSONS_SERVICE_URL+"/");
		return response;
	}
	
	private APIResponse initializePersonInfoParams(String token,Person p) {
		APIResponse response  =new APIResponse();
		List<String> params = new ArrayList<String>();
		params.add("Authorization:"+"Bearer "+token);
		response.setHeaderParams(APIUtil.addSplitParams(params));
		params = new ArrayList<String>();
/*		params.add("page:2");
		params.add("pageSize:2");*/
		response.setRequestParams(APIUtil.addSplitParams(params));
		response.setApiUrl(PERSONS_SERVICE_URL+"/"+p.getId());
		return response;
	}
	
	private APIResponse initializeCreatePersonParams(String token) {
		APIResponse response  =new APIResponse();
		List<String> params = new ArrayList<String>();
		params.add("Authorization:"+"Bearer "+token);
		response.setHeaderParams(APIUtil.addSplitParams(params));
		params = new ArrayList<String>();
		response.setJsonBody(createNewPerson());
		response.setApiUrl(PERSONS_SERVICE_URL);
		return response;
	}

	
	private APIResponse initializePersonServicesParams(String token, Person p) {
		APIResponse response  =new APIResponse();
		List<String> params = new ArrayList<String>();
		params.add("Authorization:"+"Bearer "+token);
		response.setHeaderParams(APIUtil.addSplitParams(params));
		response.setApiUrl(String.format(PERSON_PACKAGE_SERVICE_URL,p.getId()));
		return response;
	}
	private String createNewPerson() {
		Person person =new Person();
		person.setName(createName());
		person.setStatus("Active");
		person.setAddresses(createAddresses());
		person.setPhones(createPhones());
		person.setTimezone("EST5EDT");
		person.setOrganization(createOrg());
		person.setEmail("avinash.ranjalkar@covisint.com");
		person.setLanguage("en");
		return new Gson().toJson(person, Person.class);
	}
	private Organization createOrg() {
		Organization org = new Organization();
		org.setId(IDM_ORG_ID);
		return org;
	}
	private Phones[] createPhones() {
		Phones[] phones = new Phones[1];
		phones[0] = new Phones();
		phones[0].setNumber("232-232-5806");
		phones[0].setType("main");
		return phones;
	}
	private Addresses[] createAddresses() {
		Addresses[] addresses = new Addresses[2];
		addresses[0]=new Addresses();
		addresses[0].setCity("Detroit");
		addresses[0].setCountry("USA");
		addresses[0].setPostal("48084");
		addresses[0].setState("MI");
		return addresses;
	}
	private Name createName() {
		Name name = new Name();
		name.setGiven(IDM_GIVEN_NAME);
		name.setSurname(IDM_SURNAME);
		return name;
	}
	private String createNewPasswordPolicy(Person p) {
		Password password = new Password();
		password.setId(p.getId());
		password.setVersion(0);
		password.setUsername(DEVICE_NAME);
		password.setPassword(DEVICE_PASSWORD);
		password.setExpiration(DEVICE_EXPIRATION);
		password.setLocked(IS_LOCKED);
		password.setUnlockInstant(0l);
		password.setAuthenticationPolicy(createAuthenticationPolicy(p));
		password.setPasswordPolicy(createPasswordPolicy(p));
		String json =new Gson().toJson(password, Password.class);
		logger.debug("Password:"+json);
		return new Gson().toJson(password, Password.class);
	}
	private PasswordPolicy createPasswordPolicy(Person p) {
		PasswordPolicy pwdPolicy = new PasswordPolicy();
		pwdPolicy.setId(PASSWORD_POLICY_ID);
		pwdPolicy.setRealm(p.getRealm());
		pwdPolicy.setType(PASSWORD_POLICY_TYPE);
		return pwdPolicy;
	}
	private AuthenticationPolicy createAuthenticationPolicy(Person p) {
		AuthenticationPolicy authPolicy = new AuthenticationPolicy();
		authPolicy.setId(AUTHENTICATION_POLICY_ID);
		authPolicy.setRealm(p.getRealm());
		authPolicy.setType(AUTHENTICATION_POLICY_TYPE);
		return authPolicy;
	}
	/*
	 * 
	 */
	public void checkForUsers(){
		
	}
	/**
	 * 
	 */
	public void postToTwitter(){
		
	}
	
	private APIResponse initializeTokenParams() {
		APIResponse response  =new APIResponse();
		response.setUserName(IDM_CLIENT_ID);
		response.setPassword(IDM_CLIENT_PASSWORD);
		response.setApiUrl(TOKEN_SERVICE_URL);
		return response;
	}

}

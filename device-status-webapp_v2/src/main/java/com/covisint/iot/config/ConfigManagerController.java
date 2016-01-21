package com.covisint.iot.config;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.covisint.css.portal.response.StatusResponse;
import com.covisint.css.portal.utils.StreamConfigUtil;
import com.covisint.iot.stream.vo.StreamInfoVO;

/**
 * Controller for the Configuration requests coming to the webapp
 * @author aranjalkar
 *
 */
@CrossOrigin("*")
@Controller
public class ConfigManagerController {
	@Autowired
	ServletContext servletContext;
    @Qualifier("streamconfigurator")
    @Autowired
    private StreamConfigUtil streamConfigurator;
    @PostConstruct
    public void initStreamConfig(){
    	new StreamConfigUtil().setPath(servletContext.getRealPath("/WEB-INF/classes/streaminfo.json"));
    }
	final static Logger logger = LoggerFactory.getLogger(ConfigManagerController.class);
	@RequestMapping(value = "/config", method = RequestMethod.POST)		
	public ResponseEntity<StatusResponse> createConfig(HttpEntity<String> httpEntity) {
		logger.info("Adding a config for a stream");
		String config = httpEntity.getBody();
		boolean configAdded = streamConfigurator.addConfig(config);
		if(configAdded){
		 return new ResponseEntity<StatusResponse>(new StatusResponse().
	    		 setStatus(HttpStatus.OK.toString())
	    		 .setStatusCode("Added the config"),HttpStatus.OK);
		}else{
	     return new ResponseEntity<StatusResponse>(new StatusResponse().
	    		 setStatus(HttpStatus.CONFLICT.toString())
	    		 .setStatusCode("Another config exists for the stream id"),HttpStatus.CONFLICT);
		}
	}
	@RequestMapping(value = "/config", method = RequestMethod.GET)		
	public ResponseEntity<StreamInfoVO> getConfig(@RequestParam("streamId") String streamId) {
		logger.info("Getting a config for a stream");
		StreamInfoVO streamInfo = streamConfigurator.getStreamForStreamId(streamId);
		if(null!=streamInfo){
		 return new ResponseEntity<StreamInfoVO>(streamInfo,HttpStatus.OK);
		}else{
	     return new ResponseEntity<StreamInfoVO>(streamInfo,HttpStatus.NOT_FOUND);
		}
	}
	@RequestMapping(value = "/config", method = RequestMethod.DELETE)		
	public ResponseEntity<StatusResponse> deleteConfig(@RequestParam("streamId") String streamId) {
		logger.info("Deleting a config for a stream");
		boolean configDeleted = streamConfigurator.deleteStreamFromConfigFile(streamId);
		if(configDeleted){
		 return new ResponseEntity<StatusResponse>(new StatusResponse().
	    		 setStatus(HttpStatus.OK.toString())
	    		 .setStatusCode("Deleted the config"),HttpStatus.OK);
		}else{
	     return new ResponseEntity<StatusResponse>(new StatusResponse().
	    		 setStatus(HttpStatus.CONFLICT.toString())
	    		 .setStatusCode("Config may not exist"),HttpStatus.CONFLICT);
		}
	}
	
}

package com.covisint.css.portal.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.covisint.iot.stream.vo.StreamInfoVO;
import com.covisint.iot.stream.vo.StreamInfoVO.ProtocolSecurityAttribute;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
@Component("streamconfigurator")
public class StreamConfigUtil {

	final static Logger logger = LoggerFactory.getLogger(StreamConfigUtil.class);
	public static String jsonConfigPath;
	public static List<StreamInfoVO> streamConfigs;

	public  StreamConfigUtil setPath(String path){
		jsonConfigPath=path;
		if(null==streamConfigs){
			loadConfigs();
		}
		return this;
	}

	private void loadConfigs() {
		try{
		InputStream file = new FileInputStream(jsonConfigPath);
		String jsonConfigs = IOUtils.toString(file);
		file.close();
		Type listType = new TypeToken<List<StreamInfoVO>>() {}.getType();
		streamConfigs=new Gson().fromJson(jsonConfigs, listType);
		}catch(IOException e){
			logger.error("Error reading the stream configs from file");
		}
	}
	/**
	 * Adds a config to the stream
	 * @return
	 */
	public boolean addConfig(final String jsonFile){
		StreamInfoVO streamToAdd = GsonUtil.getObjectFromContent(jsonFile, StreamInfoVO.class);
		if(null==getStreamForStreamId(streamToAdd.id)){
			return addStreamToConfigFile(streamToAdd);
		}
		return false;		
	}

	private boolean addStreamToConfigFile(StreamInfoVO streamToAdd) {
		streamConfigs.add(streamToAdd);
		updateConfigFile();
		return true;
	}
	public boolean deleteStreamFromConfigFile(String streamId){
		for (Iterator<StreamInfoVO> iterator = streamConfigs.iterator(); iterator.hasNext();) {
			StreamInfoVO streamInfoVO = (StreamInfoVO) iterator.next();
			if(streamInfoVO.id.equalsIgnoreCase(streamId)){
				streamConfigs.remove(streamInfoVO);
				streamConfigs.removeAll(Collections.singleton(null));
				updateConfigFile();
				return true;
			}			
		}
		return false;
	}
	
	private void updateConfigFile() {
		String updatedConfigs = new GsonBuilder().setPrettyPrinting().create().toJson(streamConfigs);
		try{
			OutputStream fileStream = new FileOutputStream(jsonConfigPath);
			IOUtils.write(updatedConfigs,fileStream);
			fileStream.close();	
		}catch(IOException e){
			logger.error("Couldn't write to file",e);
		}
	}
	public StreamInfoVO getStreamForStreamId(String streamId) {
		StreamInfoVO streamFound=null;
		for (Iterator<StreamInfoVO> iterator = streamConfigs.iterator(); iterator.hasNext();) {
			StreamInfoVO streamInfoVO = (StreamInfoVO) iterator.next();
			if(null!=streamInfoVO && null!=streamInfoVO.id){
				if(streamInfoVO.id.equalsIgnoreCase(streamId)){
					return streamFound=streamInfoVO;
				}	
			}
		}
		return streamFound;
	}
	public String getStreamForStreamId(String streamId, String protocolAttribute) {
		StreamInfoVO streamFound = getStreamForStreamId(streamId);
		if(null!=streamFound){
			for(ProtocolSecurityAttribute pAtt:streamFound.protocolSecurityAttributes){
				if(pAtt.name.equalsIgnoreCase(protocolAttribute)){
					return pAtt.value;
				}
			}
		}
		return null;
	}

	
}

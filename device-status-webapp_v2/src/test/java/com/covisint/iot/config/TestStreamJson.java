package com.covisint.iot.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.covisint.css.portal.utils.GsonUtil;
import com.covisint.iot.stream.vo.StreamInfoVO;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class TestStreamJson extends GsonUtil {
	final static Logger logger = LoggerFactory.getLogger(TestStreamJson.class);
	static String content="";
	@BeforeTest
    public void init(){
		String fileName = "stream.json";
		content = getFileAsString(fileName);
    }
	@Test
	public void testReadStreamInfo(){
		List<StreamInfoVO> streamVO = GsonUtil.getListFromContent(content);
		logger.debug("the streamVO:"+streamVO.toString());
	}
	@Test
	public void testSearchStreamInfo(){
		List<StreamInfoVO> streamVO = GsonUtil.getListFromContent(content);
		String streamId="33038566-0748-47f4-ae96-075f9c9578a5";
		StreamInfoVO streamFound;
		streamFound = getStreamForStreamId(streamVO, streamId);	
		logger.debug(streamFound.producerTopic);
		logger.debug("the streamVO:"+streamVO.toString());
	}
	private StreamInfoVO getStreamForStreamId(List<StreamInfoVO> streamVO, String streamId) {
		StreamInfoVO streamFound=null;
		for (StreamInfoVO stream: streamVO){
			if(stream.id.equalsIgnoreCase(streamId)){
				streamFound = stream;
			}
		}
		return streamFound;
	}


	private String getFileAsString(String fileName) {
		String content = "";
		InputStream file = ClassLoader.getSystemResourceAsStream(fileName);
		try {
			content = IOUtils.toString(file);
			logger.debug("Content:" + content);
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}
}

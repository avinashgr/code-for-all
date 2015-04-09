package com.covisint.cisco.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.testng.annotations.DataProvider;



public class FileUtil {
    @SuppressWarnings("unchecked")
    public static List<String> getRawLinesFromFile() throws Exception
    {
    	Class c= Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
    	Method m= c.getMethod(Thread.currentThread().getStackTrace()[2].getMethodName());
    	String fileName =(m.getAnnotation(DataProvider.class).name())+".txt";    	
    	InputStream in = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
        List<String> lines = IOUtils.readLines(in, "UTF-8");
        in.close();
        return lines;
    }
    
    public static List[] getRequestParamByType(List<String> list) {
    	List<String>[] params = new ArrayList[3];
    	for(int i =0;i<params.length;i++){
    		params[i] = new ArrayList<String>();
    	}
    	String type="REQUESTHEADER";
    	list.remove(0);
    	String url=list.get(list.size()-1);

    	params[2].add(url);
    	list.remove(list.size()-1);
    	for(String param: list){
    		if(param.contains("Body----")){
    			type="REQUESTBODY";
    		}else{
    			if(type.equalsIgnoreCase("REQUESTBODY")){
    				params[1].add(param);
    			}else{				
    				params[0].add(param);
    			}
    		}
    	}
    	return params;
      }
}

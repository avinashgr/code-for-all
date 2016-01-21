package com.covisint.css.portal.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.StringUtils;
/**
 * Overrides the base class to retrieve resolved props and expose them for 
 * further use in other classes
 *
 */
public class ExposablePropertyPlaceholderConfigurer extends	PropertyPlaceholderConfigurer {
	private Map<String,String> propertiesMap;
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		propertiesMap = new HashMap<String,String>();
		for(Object key: props.keySet()){
			String keyName= key.toString();
			propertiesMap.put(keyName, resolvePlaceholder(keyName,props));
		}
	}
	
	public String getProperty(String propertyName,String defaultValue){
		String value = propertiesMap.get(propertyName);
		if(StringUtils.isEmpty(value)){
			return defaultValue;
		}else{
			return value;
		}
	}

}

package com.goeuro.dao;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;

import com.goeuro.controller.BusRoutesChallengeConfiguration;

public class DataProviderFactory {
	final static Logger logger = Logger.getLogger(DataProviderFactory.class);

	public static IDataProvider getDataProvider(DataProviderType type){
		switch(type){
		case FILE:
			return FileDataProvider.getInstance();
		default:
			break;
		}
		
		
		return null;
	}
	
	public static IDataProvider getDataProvider(){
		switch(BusRoutesChallengeConfiguration.getInstance().getDataProviderType()){
		case FILE:
			return FileDataProvider.getInstance();
		case URL:
			break;
		default:
			break;
		}
		
		
		return null;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}

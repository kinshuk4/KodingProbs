package com.goeuro.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goeuro.dao.DataProviderType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;


public class BusRoutesChallengeConfiguration{
	
	
    private static class SingletonHelper{
        private static final BusRoutesChallengeConfiguration INSTANCE = new BusRoutesChallengeConfiguration();
    }
    
    public static BusRoutesChallengeConfiguration getInstance(){
        return SingletonHelper.INSTANCE;
    }
    
    
    @NotEmpty
    private String busRoutesFile;
    
    private DataProviderType dataProviderType;
    

    @JsonProperty
    public String getBusRoutesFile() {
        return busRoutesFile;
    }

    @JsonProperty
    public void setBusRoutesFile(String busRoutesFile) {
        this.busRoutesFile = busRoutesFile;
    }
    
    
    
    public DataProviderType getDataProviderType() {
		return dataProviderType;
	}

	public void setDataProviderType(DataProviderType busRouteSourceFile) {
		this.dataProviderType = busRouteSourceFile;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}

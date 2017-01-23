package com.trivago.pipeline.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.ObjectMapper;


public class Hotel implements Serializable {

	private static final long serialVersionUID = 6180479627671834797L;
	private String name;
    private String address;
    private String stars;
    private String contact;
    private String phone;
    private String uri;

    public Hotel(){
    	
    }

    public Hotel(String name, String address, String stars, String contact, String phone, String uri) {
        this.name = name;
        this.address = address.replaceAll("^\"|\"$", "");
        this.stars = stars;
        this.contact = contact;
        this.phone = phone;
        this.uri = uri;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
    
    public String toSerializableString() throws Exception {
		return new ObjectMapper().writeValueAsString(this);
	}
	
	
	public void fromSerializedString(final String serializedData) throws Exception {
		final Hotel readValue = new ObjectMapper().readValue(serializedData, this.getClass());
		this.name = readValue.name;
		this.address = readValue.address;
		this.contact = readValue.contact;
		this.phone = readValue.phone;
		this.uri = readValue.uri;
		this.stars = readValue.stars;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}

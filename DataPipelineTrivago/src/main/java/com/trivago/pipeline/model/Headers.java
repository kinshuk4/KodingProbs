package com.trivago.pipeline.model;

/**
 * Created by Chaklader on 11/19/16.
 */
public class Headers {

    private String name;
    private String address;
    private String stars;
    private String contact;
    private String phone;
    private String uri;

    public Headers(String name, String address, String stars, String contact, String phone, String uri) {
        this.name = name;
        this.address = address;
        this.stars = stars;
        this.contact = contact;
        this.phone = phone;
        this.uri = uri;
    }

    public Headers() {
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
}

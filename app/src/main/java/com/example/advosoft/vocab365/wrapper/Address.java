package com.example.advosoft.vocab365.wrapper;

import java.io.Serializable;

/**
 * Created by kipl188 on 5/6/2016.
 */
public class Address implements Serializable {
    private String locationName = "";
    private String streetName = "";
    private String city = "";
    private String state = "";
    private String country = "";

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return locationName+", "+streetName+", "+city+", "+state+", "+country;
    }
}

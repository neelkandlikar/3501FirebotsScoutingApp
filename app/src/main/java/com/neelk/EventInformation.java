package com.neelk.robotics;

import android.support.annotation.NonNull;

public class EventInformation implements Comparable<EventInformation> {
    private String name;
    private Date date;
    private String location;
    private int lng;
    private int lat;
    private String eventCode;

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public EventInformation(String eventCode, String name, String location, Date date, int lng, int lat) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.lng = lng;
        this.lat = lat;
        this.eventCode = eventCode;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLng() {
        return lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    @Override
    public int compareTo(@NonNull EventInformation eventInformation) {
        if (date.isAfter(eventInformation.getDate())) {
            return 1;
        }
        return -1;

    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Event Code: " + eventCode + " Name: " + name + " Location: " + location + " Date: " + date + " lat: " + lat + " lat : " + lat;
    }
}

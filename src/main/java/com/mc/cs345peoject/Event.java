/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mc.cs345peoject;

/**
 *
 * @author halu
 */
public class Event {

    public int eventId;
    public String eventName;
    public String eventAuth;
    public String eventTime;
    public String eventDesc;
    public String eventLat;
    public String eventLng;
    public String eventMsg;
    public String eventTag;
    private String eventPublishAt;
    private String eventExpireAt;

    public Event() {
    }

    public Event(int eventId, String eventName, String eventAuth, String eventTime, String eventDesc, String eventLat, String eventLng, String eventMsg) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventAuth = eventAuth;
        this.eventTime = eventTime;
        this.eventDesc = eventDesc;
        this.eventLat = eventLat;
        this.eventLng = eventLng;
        this.eventMsg = eventMsg;
    }

    public String getEventAuth() {
        return eventAuth;
    }

    public void setEventAuth(String eventAuth) {
        this.eventAuth = eventAuth;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventLat() {
        return eventLat;
    }

    public void setEventLat(String eventLat) {
        this.eventLat = eventLat;
    }

    public String getEventLng() {
        return eventLng;
    }

    public void setEventLng(String eventLng) {
        this.eventLng = eventLng;
    }

    public String getEventMsg() {
        return eventMsg;
    }

    public void setEventMsg(String eventMsg) {
        this.eventMsg = eventMsg;
    }
    
    public String getEventTag() {
        return eventTag;
    }

    public void setEventTag(String eventTag) {
        this.eventTag = eventTag;
    }

    /**
     * @return the eventPublishAt
     */
    public String getEventPublishAt() {
        return eventPublishAt;
    }

    /**
     * @param eventPublishAt the eventPublishAt to set
     */
    public void setEventPublishAt(String eventPublishAt) {
        this.eventPublishAt = eventPublishAt;
    }

    /**
     * @return the eventExpireAt
     */
    public String getEventExpireAt() {
        return eventExpireAt;
    }

    /**
     * @param eventExpireAt the eventExpireAt to set
     */
    public void setEventExpireAt(String eventExpireAt) {
        this.eventExpireAt = eventExpireAt;
    }
    
}

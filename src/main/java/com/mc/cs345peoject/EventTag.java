/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mc.cs345peoject;

/**
 *
 * @author halu
 */
public class EventTag {
    private int eventTagId;
    private int eventId;
    private String eventTag;
    

    public EventTag(int eventTagId, int eventId, String eventTag) {
        this.eventTagId = eventTagId;
        this.eventId = eventId;
        this.eventTag = eventTag;
    }

    public EventTag() {
    }

    public int getEventTagId() {
        return eventTagId;
    }

    public void setEventTagId(int eventTagId) {
        this.eventTagId = eventTagId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventTag() {
        return eventTag;
    }

    public void setEventTag(String eventTag) {
        this.eventTag = eventTag;
    }
}

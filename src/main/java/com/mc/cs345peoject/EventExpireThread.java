/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mc.cs345peoject;

import com.google.firebase.messaging.FirebaseMessagingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author halu
 */
public class EventExpireThread {

    private String eventExpireDate;
    private String userToken;
    private int eventId;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public void runTimer(String receiveEventExpireDate, String receiveUserToken,
             String eventTitle, int reveiveEventId) {
        eventExpireDate = receiveEventExpireDate;
        userToken = receiveUserToken;
        eventId = reveiveEventId;
        Date expireDate = null;

        try {
            expireDate = sdf.parse(eventExpireDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    CloudMessage.sendNotification(userToken, eventTitle, "event_expire", Integer.toString(eventId));
                } catch (FirebaseMessagingException ex) {
                    Logger.getLogger(EventExpireThread.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };
        timer.schedule(task, expireDate);
    }
}

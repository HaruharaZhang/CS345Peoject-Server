/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mc.cs345peoject;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author halu
 */
public class CloudMessage {

    public CloudMessage() {
        initialize();
    }

    public static void initialize() {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/java/com/mc/cs345peoject/cs354finalproject-308d4-firebase-adminsdk-cvm3z-3d1558c1b8.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://cs354finalproject-308d4-default-rtdb.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public void sendNotification(String deviceToken, String title, String body) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();
        Message message = Message.builder()
                .setNotification(notification)
                .putData("title", title)
                .putData("body", body)
                .setToken(deviceToken)
                .build();
        String response = FirebaseMessaging.getInstance().send(message);
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mc.cs345peoject;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author halu
 */
public class Auth {

    public static void initialize() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("src/main/java/com/mc/cs345peoject/cs354finalproject-308d4-firebase-adminsdk-cvm3z-3d1558c1b8.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://cs354finalproject-308d4-default-rtdb.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
    }

    public boolean verifyUserWithToken(String idToken) {
        FirebaseToken decodedToken;
        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            System.out.println(uid);
            return true;
        } catch (FirebaseAuthException ex) {
            Logger.getLogger(Auth.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}

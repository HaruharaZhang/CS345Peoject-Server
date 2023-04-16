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
import static com.mc.cs345peoject.MyResource.DB_PASS;
import static com.mc.cs345peoject.MyResource.DB_URL;
import static com.mc.cs345peoject.MyResource.DB_USER;
import static com.mc.cs345peoject.MyResource.JDBC_DRIVER;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author halu
 */
public class CloudMessage {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/event?useSSL=false&serverTimezone=UTC";
    static final String DB_USER = "root";
    static final String DB_PASS = "12345678";
    Connection conn = null;
    //https://maps.googleapis.com/maps/api/geocode/json?latlng=12.1123,3.23&key=VExBnbS3vTwT9Bsn5X6F0VuJ8yY=
    private static final String URL_FOR_GOOGLE_API = "https://maps.googleapis.com/maps/api/geocode/json?latlng=%f,%f&result_type=locality&address_type=administrative_area_level_1&key=%s";
    static String apiKey = "AIzaSyAwnco1LKoNK_W11slJj8mnTafHMbVVM9c";

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

    public static void sendNotification(String deviceToken, String title, String body, String event_id) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();
        Message message = Message.builder()
                .setNotification(notification)
                .putData("title", title)
                .putData("body", body)
                .putData("event_id", event_id)
                .setToken(deviceToken)
                .build();
        String response = FirebaseMessaging.getInstance().send(message);
    }

    //这个方法会检查用户新发布的Event是否被某些用户订阅
    //如果用户city和已定于的event对上了，则发送通知给指定用户
    public void checkUserSubscribe(int eventId, String eventTag) {
        //String lat, String lng, String eventTag, String title, String body
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            String featchSql = "select * from event_table where event_id = ?;";

            PreparedStatement stmt = null;
            stmt = conn.prepareStatement(featchSql);
            stmt.setInt(1, eventId);
            ResultSet eventRs = stmt.executeQuery();
            String lat = "";
            String lng = "";
            String title = "";
            String body = "";
            while (eventRs.next()) {
                lat = eventRs.getString("event_lat");
                lng = eventRs.getString("event_lng");
                title = eventRs.getString("event_name");
                body = "new_event";
            }

            String city = findCity(Double.valueOf(lat), Double.valueOf(lng));

            String sql = "select DISTINCT user_device_token from event_subscribe "
                    + "where user_city = ? and tag = ?;";
            stmt = null;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, city);
            stmt.setString(2, eventTag);
            ResultSet rs = stmt.executeQuery();
            //如果返回数据为空，说明没有用户订阅该tag
            if (rs.isBeforeFirst() == false) {
                conn.close();
                rs.close();
            } else {
                //如果返回数据不为空，说明有用户订阅，发送通知。
                while (rs.next()) {
                    sendNotification(rs.getString("user_device_token"), title, body, String.valueOf(eventId));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String findCity(double lat, double lng) throws IOException {
        // 构建请求URL
        String urlStr = String.format(URL_FOR_GOOGLE_API, lat, lng, apiKey);
        URL url = new URL(urlStr);

        // 发送HTTP GET请求
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // 解析响应
        try (InputStream stream = conn.getInputStream()) {
            Scanner scanner = new Scanner(stream).useDelimiter("\\A");
            String responseStr = scanner.hasNext() ? scanner.next() : "";

            // 从响应中提取城市信息
            String city = extractCityFromResponse(responseStr);
            return city;
        }
    }

    private static String extractCityFromResponse(String responseStr) {
        // 解析响应JSON，提取城市信息
        // 这里只提取了第一个结果中的城市信息
        // 实际应用中可以根据自己的需求选择不同的级别和结果
        JSONObject response = new JSONObject(responseStr);
        JSONArray results = response.getJSONArray("results");
        JSONObject firstResult = results.getJSONObject(0);
        JSONArray addressComponents = firstResult.getJSONArray("address_components");
        for (int i = 0; i < addressComponents.length(); i++) {
            JSONObject component = addressComponents.getJSONObject(i);
            JSONArray types = component.getJSONArray("types");
            if (types.toString().contains("\"administrative_area_level_2\"") && types.toString().contains("\"political\"")) {
                return component.getString("long_name");
            }
        }
        return null;
    }

}

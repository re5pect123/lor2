package com.webapp.lora.controller;

import com.webapp.lora.entity.Group;
import com.webapp.lora.model.NotificationData;
import com.webapp.lora.model.NotificationRequestModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.lang.reflect.Type;

@RestController
public class PushNotificationController {

    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Group.class);

    @GetMapping("/test-notification")
    public void test() throws IOException {
        logger.info("*** SEND NOTIFICATION! ***");

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(
                "https://fcm.googleapis.com/fcm/send");

        NotificationRequestModel notificationRequestModel = new NotificationRequestModel();
        NotificationData notificationData = new NotificationData();

        notificationData.setDetail("Senzor je aktivan!");
        notificationData.setTitle("Lora");
        notificationRequestModel.setData(notificationData);
        notificationRequestModel.setTo("/topics/all");

        Gson gson = new Gson();
        Type type = new TypeToken<NotificationRequestModel>() {
        }.getType();

        String json = gson.toJson(notificationRequestModel, type);

        StringEntity input = new StringEntity(json);
        input.setContentType("application/json");

        postRequest.addHeader("Authorization", "key=AAAA0_QWZXc:APA91bEzNN4vmGlwhKnIbulLa7YoLbkb1Gq9rz3XiGBIUgcsM4COD8WkmLbvt0eRo5eko51nMquC5kq0RaQ1bmaRIra1-AW0uObJegvKeHt-XLd9PtMPLSBEx5_bIvP-j5Zt5iel9iqL");
        postRequest.setEntity(input);

        logger.info("request:" + json);

        HttpResponse response = httpClient.execute(postRequest);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatusLine().getStatusCode());
        } else if (response.getStatusLine().getStatusCode() == 200) {

            logger.info("response:" + EntityUtils.toString(response.getEntity()));

        }
    }
}

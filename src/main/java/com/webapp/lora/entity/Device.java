package com.webapp.lora.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    String devEUI; // unique ID of end device
    String appEUI; // aplication server
    String appKey; // message for device
    String userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDevEUI() {
        return devEUI;
    }

    public void setDevEUI(String devEUI) {
        this.devEUI = devEUI;
    }

    public String getAppEUI() {
        return appEUI;
    }

    public void setAppEUI(String appEUI) {
        this.appEUI = appEUI;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

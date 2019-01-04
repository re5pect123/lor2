package com.webapp.lora.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "device_db")
public class DeviceDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String devEUI;
    String payload_hex;
    Timestamp time;

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

    public String getPayload_hex() {
        return payload_hex;
    }

    public void setPayload_hex(String payload_hex) {
        this.payload_hex = payload_hex;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}

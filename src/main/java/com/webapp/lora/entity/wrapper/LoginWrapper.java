package com.webapp.lora.entity.wrapper;

import com.webapp.lora.entity.Device;
import com.webapp.lora.entity.Group;
import com.webapp.lora.entity.User;

public class LoginWrapper {

    User user;
    Device device;
    Group group;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "LoginWrapper{" +
                "user=" + user +
                ", device=" + device +
                ", group=" + group +
                '}';
    }
}

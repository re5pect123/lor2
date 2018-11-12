package com.webapp.lora.controller;

import com.webapp.lora.entity.Device;
import com.webapp.lora.entity.User;
import com.webapp.lora.entity.wrapper.LoginWrapper;
import com.webapp.lora.service.DeviceService;
import com.webapp.lora.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @Autowired
    UserService userService;

    @PostMapping("/add-device")
    public Map addDevice(@RequestBody LoginWrapper loginWrapper){
        User userDb = userService.findAllByUserNameAndPassword(
                loginWrapper.getUser().getUserName(),
                loginWrapper.getUser().getPassword());

        if(userDb != null){
            loginWrapper.getDevice().setUserId(String.valueOf(userDb.getId()));
            deviceService.addDevice(loginWrapper.getDevice());

            return Collections.singletonMap("message","sucess add device");
        }
        return Collections.singletonMap("message","check login");
    }

    @PostMapping("/find-all-devices")
    public List<Device> findAllDevices(@RequestBody User user){
        User userDb = userService.findAllByUserNameAndPassword(
                user.getUserName(),
                user.getPassword());

        if(userDb != null){
            List<Device> allDevicesForUser = deviceService.findAllByUserId(String.valueOf(userDb.getId()));
            return allDevicesForUser;
        }
        return null;
    }

    @PostMapping("/find-one-device")
    public Device findByDevEUI(@RequestBody LoginWrapper loginWrapper){
        User userDb = userService.findAllByUserNameAndPassword(
                loginWrapper.getUser().getUserName(),
                loginWrapper.getUser().getPassword());

        if(userDb != null){
            System.out.println("EUI " + loginWrapper.getDevice().getDevEUI());
            return deviceService.findByDevEUI(loginWrapper.getDevice().getDevEUI());
        }
        return null;
    }
}

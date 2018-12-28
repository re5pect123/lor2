package com.webapp.lora.controller;

import com.webapp.lora.entity.Device;
import com.webapp.lora.entity.Group;
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

        Device existDevice = deviceService.findByDevEUI(loginWrapper.getDevice().getDevEUI());
        if (existDevice != null){
            if (existDevice.getDevEUI().equals(loginWrapper.getDevice().getDevEUI())){
                return Collections.singletonMap("message", "Uređaj je već aktiviran");
            }
        } else
        if(userDb != null){
            loginWrapper.getDevice().setUserId(String.valueOf(userDb.getId()));
            loginWrapper.getDevice().setStatus("Deaktiviran");
            loginWrapper.getDevice().setBatteryStatus("89");
            deviceService.addDevice(loginWrapper.getDevice());
            return Collections.singletonMap("message","Uspešno ste dodali novi uređaj");
        }
        return Collections.singletonMap("message","Pogrešni pristupni parametri");
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
            return deviceService.findByDevEUI(loginWrapper.getDevice().getDevEUI());
        }
        return null;
    }

    @PostMapping("/set-group-status")
    public Map groupStatus(@RequestBody LoginWrapper loginWrapper){
        User userDb = userService.findAllByUserNameAndPassword(
                loginWrapper.getUser().getUserName(),
                loginWrapper.getUser().getPassword());

        if (userDb != null) {
            List<Device> devices = deviceService.findAllByUserId(String.valueOf(userDb.getId()));

            for (int i = 0; i < devices.size(); i++) {
                if (Integer.valueOf(devices.get(i).getGroupId()) == loginWrapper.getGroup().getId()) {
                    Device dev = devices.get(i);
                    dev.setStatus(loginWrapper.getDevice().getStatus());
                    System.out.println(dev);
                    deviceService.addDevice(dev);
                }
            }
        }else {
            return Collections.singletonMap("message", "Korisnik nije registrovan");
        }
        return Collections.singletonMap("message", "Status grupe je " + loginWrapper.getDevice().getStatus());
    }

    @PostMapping("/set-device-status")
    public Map deviceStatus(@RequestBody LoginWrapper loginWrapper){
        User userDb = userService.findAllByUserNameAndPassword(
                loginWrapper.getUser().getUserName(),
                loginWrapper.getUser().getPassword());

        if (userDb != null) {
            List<Device> devices = deviceService.findAllByUserId(String.valueOf(userDb.getId()));

            for (int i = 0; i < devices.size(); i++) {
                if (Integer.valueOf(devices.get(i).getId()) == loginWrapper.getDevice().getId()) {
                    Device dev = devices.get(i);
                    dev.setStatus(loginWrapper.getDevice().getStatus());
                    System.out.println(dev);
                    deviceService.addDevice(dev);
                }
            }
        }else {
            return Collections.singletonMap("message", "Korisnik nije registrovan");
        }
        return Collections.singletonMap("message", "Status uredjaja je " + loginWrapper.getDevice().getStatus());
    }

}

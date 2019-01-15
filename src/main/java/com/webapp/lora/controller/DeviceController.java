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
import java.util.logging.Logger;

@RestController
public class DeviceController {

    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DeviceController.class);

    @Autowired
    DeviceService deviceService;

    @Autowired
    UserService userService;

    @PostMapping("/add-device")
    public Map addDevice(@RequestBody LoginWrapper loginWrapper){
        logger.info("Request add-device: " + loginWrapper);
        User userDb = userService.findAllByUserNameAndPassword(
                loginWrapper.getUser().getUserName(),
                loginWrapper.getUser().getPassword());

        Device existDevice = deviceService.findByDevEUI(loginWrapper.getDevice().getDevEUI());
        if (existDevice != null){
            if (existDevice.getDevEUI().equals(loginWrapper.getDevice().getDevEUI())){
                logger.info("Response: " + "Uređaj je već aktiviran");
                return Collections.singletonMap("message", "Uređaj je već aktiviran");
            }
        } else
        if(userDb != null){
            loginWrapper.getDevice().setUserId(String.valueOf(userDb.getId()));
            loginWrapper.getDevice().setStatus("Deaktiviran");
            loginWrapper.getDevice().setBatteryStatus("89");
            loginWrapper.getDevice().setTemperature("24.3");
            deviceService.addDevice(loginWrapper.getDevice());
            logger.info("Response: " + "Uspešno ste dodali novi uređaj");
            return Collections.singletonMap("message","Uspešno ste dodali novi uređaj");
        }
        logger.info("Response: " + "Pogrešni pristupni parametri");
        return Collections.singletonMap("message","Pogrešni pristupni parametri");
    }

    @PostMapping("/find-all-devices")
    public List<Device> findAllDevices(@RequestBody User user){
        logger.info("Request find-all-devices: " + user);
        User userDb = userService.findAllByUserNameAndPassword(
                user.getUserName(),
                user.getPassword());

        if(userDb != null){
            List<Device> allDevicesForUser = deviceService.findAllByUserId(String.valueOf(userDb.getId()));
            logger.info("Response: " + allDevicesForUser);
            return allDevicesForUser;
        }
        logger.info("Response null: " + " Korisnik ne postoji");
        return null;
    }

    @Deprecated
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
        logger.info("Request set-group-status: " + loginWrapper);
        User userDb = userService.findAllByUserNameAndPassword(
                loginWrapper.getUser().getUserName(),
                loginWrapper.getUser().getPassword());

        if (userDb != null) {
            List<Device> devices = deviceService.findAllByUserId(String.valueOf(userDb.getId()));

            for (int i = 0; i < devices.size(); i++) {
                if (Integer.parseInt(devices.get(i).getGroupId()) == loginWrapper.getGroup().getId()) {
                    Device dev = devices.get(i);
                    dev.setStatus(loginWrapper.getDevice().getStatus());
                    logger.info("Set status za grupu uredjaja " + dev);
                    deviceService.addDevice(dev);
                } else{
                    logger.info("Response:  Proverite id grupe uredjaja izabrali ste id koji nije postojeći");
                    return Collections.singletonMap("message", "Proverite id grupe uredjaja izabrali ste id koji nije postojeći");
                }
            }
        }else {
            logger.info("Response: " + "Korisnik nije registrovan");
            return Collections.singletonMap("message", "Korisnik nije registrovan");
        }
        logger.info("Response:  Status grupe je: " + loginWrapper.getDevice().getStatus());
        return Collections.singletonMap("message", "Status grupe je " + loginWrapper.getDevice().getStatus());
    }

    @PostMapping("/set-device-status")
    public Map deviceStatus(@RequestBody LoginWrapper loginWrapper){
        logger.info("Request set-device-status: " + loginWrapper);
        User userDb = userService.findAllByUserNameAndPassword(
                loginWrapper.getUser().getUserName(),
                loginWrapper.getUser().getPassword());

        if (userDb != null) {
            List<Device> devices = deviceService.findAllByUserId(String.valueOf(userDb.getId()));

            for (int i = 0; i < devices.size(); i++) {
                logger.info("INTEGER.VALUE OF " + Integer.valueOf(devices.get(i).getId()) + " Login wrapper " + loginWrapper.getDevice().getId());
                logger.info("devices size " + devices.size());

                if (devices.get(i).getId() == loginWrapper.getDevice().getId()) {
                    Device dev = devices.get(i);
                    dev.setStatus(loginWrapper.getDevice().getStatus());
                    logger.info("Set status za uredjaj " + dev);
                    deviceService.addDevice(dev);
                }else{
                    logger.info("Response:  Proverite id uredjaja izabrali ste id koji nije postojeći");
                }
            }
        }else {
            logger.info("Response: " + "Korisnik nije registrovan");
            return Collections.singletonMap("message", "Korisnik nije registrovan");
        }
        logger.info("Response:  Status uredjaja je: " + loginWrapper.getDevice().getStatus());
        return Collections.singletonMap("message", "Status uredjaja je " + loginWrapper.getDevice().getStatus());
    }

}

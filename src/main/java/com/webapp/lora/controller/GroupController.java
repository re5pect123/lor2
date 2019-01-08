package com.webapp.lora.controller;

import com.webapp.lora.entity.Device;
import com.webapp.lora.entity.Group;
import com.webapp.lora.entity.User;
import com.webapp.lora.entity.wrapper.LoginWrapper;
import com.webapp.lora.service.DeviceService;
import com.webapp.lora.service.GroupService;
import com.webapp.lora.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class GroupController {

    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Group.class);

    @Autowired
    GroupService groupService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    UserService userService;

    @PostMapping("/create-group")
    public Map saveGroup(@RequestBody LoginWrapper loginWrapper){
        logger.info("Request create-group: " + loginWrapper);
        User userDb = userService.findAllByUserNameAndPassword(
                loginWrapper.getUser().getUserName(),
                loginWrapper.getUser().getPassword());

        if(userDb != null){
            List<Group> existGroup = groupService.findAllByUserId(String.valueOf(userDb.getId()));
            if (existGroup != null){
                for(int i = 0; i < existGroup.size(); i ++){
                    if (existGroup.get(i).getName().equals(loginWrapper.getGroup().getName())){
                        logger.info("Response: " + "Grupa sa imenom " + loginWrapper.getGroup().getName() + " već postoji");
                        return Collections.singletonMap("message","Grupa sa imenom " + loginWrapper.getGroup().getName() + " već postoji");
                    }
                }
            }
            loginWrapper.getGroup().setUserId(String.valueOf(userDb.getId()));

            groupService.saveGroup(loginWrapper.getGroup());
            logger.info("Response: " + "Uspešno ste dodali novu grupu");
            return Collections.singletonMap("message","Uspešno ste dodali novu grupu");
        }
        logger.info("Response: " + "Pogrešni pristupni parametri");
        return Collections.singletonMap("message","Pogrešni pristupni parametri");
    }

    @PostMapping("/find-all-group")
    public List<Group> findAllGroup(@RequestBody User user){
        logger.info("Request find-all-group: " + user);
        User userDb = userService.findAllByUserNameAndPassword(
                user.getUserName(),
                user.getPassword());
        if (userDb != null){
            return groupService.findAllByUserId(String.valueOf(userDb.getId()));
        }
        return null;
    }

    @PostMapping("/add-device-to-group")
    public Map addDeviceToGroup(@RequestBody LoginWrapper loginWrapper){
        logger.info("Request add-device-to-group: " + loginWrapper);
        User userDb = userService.findAllByUserNameAndPassword(
                loginWrapper.getUser().getUserName(),
                loginWrapper.getUser().getPassword());

        if(userDb != null) {
            List<Group> groups = groupService.findAllByUserId(String.valueOf(userDb.getId()));
            List<Device> devices = deviceService.findAllByUserId(String.valueOf(userDb.getId()));

            Device dev = null;
            for (int i = 0; i < devices.size(); i++) {
                if (devices.get(i).getId() == loginWrapper.getDevice().getId()) {
                    dev = devices.get(i);
                    System.out.println(dev);
                }
            }
            if (dev == null) {
                logger.info("Response: " + "Ne postoji traženi uređaj");
                return Collections.singletonMap("message", "Ne postoji traženi uređaj");
            }

            Group group = null;
            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).getId() == loginWrapper.getGroup().getId()) {
                    group = groups.get(i);

                    System.out.println(String.valueOf(groups.get(i).getId()));
                    dev.setGroupId(String.valueOf(groups.get(i).getId()));

                    deviceService.addDevice(dev);
                    logger.info("Response: " + "Uspešno ste dodali uređaj u grupu");
                    return Collections.singletonMap("message", "Uspešno ste dodali uređaj u grupu");
                }

            }
            if (group == null) {
                logger.info("Response: " + "Ne postoji tražena grupa");
                return Collections.singletonMap("message", "Ne postoji tražena grupa");
            }
            logger.info("Response: " + "Korisnik ne postoji");
        } return Collections.singletonMap("message", "Korisnik ne postoji");
    }

    @Deprecated
    @PostMapping("/find-all-device-by-group")
    public List<Group> findAllGroup(@RequestBody LoginWrapper loginWrapper){

        User userDb = userService.findAllByUserNameAndPassword(
                loginWrapper.getUser().getUserName(),
                loginWrapper.getUser().getPassword());

        return groupService.findAllByDeviceId(String.valueOf(loginWrapper.getDevice().getId()));
    }

    @PostMapping("/activate-group")
    public Map activateGroup(@RequestBody LoginWrapper loginWrapper){
        User userDb = userService.findAllByUserNameAndPassword(
                loginWrapper.getUser().getUserName(),
                loginWrapper.getUser().getPassword());

        Group updateGroup = groupService.findByName(loginWrapper.getGroup().getName());

        loginWrapper.getGroup().setId(updateGroup.getId());
        loginWrapper.getGroup().setName(updateGroup.getName());

        loginWrapper.getGroup().setUserId(updateGroup.getUserId());

        groupService.saveGroup(loginWrapper.getGroup());
        return Collections.singletonMap("message","sucess activate group");
    }

    @PostMapping("/deactivate-group")
    public Map deactivateGroup(@RequestBody LoginWrapper loginWrapper){
        User userDb = userService.findAllByUserNameAndPassword(
                loginWrapper.getUser().getUserName(),
                loginWrapper.getUser().getPassword());

        Group updateGroup = groupService.findByName(loginWrapper.getGroup().getName());

        loginWrapper.getGroup().setId(updateGroup.getId());
        loginWrapper.getGroup().setName(updateGroup.getName());

        loginWrapper.getGroup().setUserId(updateGroup.getUserId());

        groupService.saveGroup(loginWrapper.getGroup());
        return Collections.singletonMap("message","sucess deactivate group");
    }
}

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

    @Autowired
    GroupService groupService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    UserService userService;

    @PostMapping("/create-group")
    public Map saveGroup(@RequestBody LoginWrapper loginWrapper){
        User userDb = userService.findAllByUserNameAndPassword(
                loginWrapper.getUser().getUserName(),
                loginWrapper.getUser().getPassword());

        List<Group> existGroup = groupService.findAllByUserId(String.valueOf(userDb.getId()));
        for(int i = 0; i < existGroup.size(); i ++){
            if (existGroup.get(i).getName().equals(loginWrapper.getGroup().getName())){
                return Collections.singletonMap("message","this group exist in db, create group with other name");
            }
        }
        if(userDb != null){
            loginWrapper.getGroup().setUserId(String.valueOf(userDb.getId()));

            groupService.saveGroup(loginWrapper.getGroup());
            return Collections.singletonMap("message","sucess add new group");
        }
        return Collections.singletonMap("message","check login");
    }

    @PostMapping("/find-all-group")
    public List<Group> findAllGroup(@RequestBody User user){

        User userDb = userService.findAllByUserNameAndPassword(
                user.getUserName(),
                user.getPassword());

        return groupService.findAllByUserId(String.valueOf(userDb.getId()));
    }

    @PostMapping("/add-device-to-group")
    public Map addDeviceToGroup(@RequestBody LoginWrapper loginWrapper){
        User userDb = userService.findAllByUserNameAndPassword(
                loginWrapper.getUser().getUserName(),
                loginWrapper.getUser().getPassword());

        if(userDb != null) {
            // sve grupe koje pripadaju tom useru
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
                return Collections.singletonMap("message", "Ne postoji trazeni uredjaj.");
            }

            Group group = null;
            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).getId() == loginWrapper.getGroup().getId()) {
                    group = groups.get(i);

                    System.out.println(String.valueOf(groups.get(i).getId()));
                    dev.setGroupId(String.valueOf(groups.get(i).getId()));

                    deviceService.addDevice(dev);

                    return Collections.singletonMap("message", "Uspesno ste dodali uredjaj u grupu.");
                }

            }
            if (group == null) {
                return Collections.singletonMap("message", "Ne postoji trazena grupa");
            }
        } return Collections.singletonMap("message", "Korisnik ne postoji");
    }

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

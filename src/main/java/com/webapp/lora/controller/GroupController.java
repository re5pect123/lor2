package com.webapp.lora.controller;

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

        if(userDb != null){
            loginWrapper.getGroup().setUserId(String.valueOf(userDb.getId()));
            groupService.saveGroup(loginWrapper.getGroup());
            return Collections.singletonMap("message","sucess add group");
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

        if(userDb != null){

            List<Group> groups = groupService.findAllByUserId(String.valueOf(userDb.getId()));
            loginWrapper.getGroup().setId(groups.get(0).getId());
            loginWrapper.getGroup().setDeviceId(String.valueOf(userDb.getId()));
            loginWrapper.getGroup().setDeviceId(String.valueOf(loginWrapper.getDevice().getId()));
            
            groupService.saveGroup(loginWrapper.getGroup());

            return Collections.singletonMap("message","sucess add group");
        }
        return Collections.singletonMap("message","check login");
    }

    @PostMapping("/find-all-device-by-group")
    public List<Group> findAllGroup(@RequestBody LoginWrapper loginWrapper){

        User userDb = userService.findAllByUserNameAndPassword(
                loginWrapper.getUser().getUserName(),
                loginWrapper.getUser().getPassword());

        return groupService.findAllByDeviceId(String.valueOf(loginWrapper.getDevice().getId()));
    }
}

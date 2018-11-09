package com.webapp.lora.controller;

import com.webapp.lora.entity.User;
import com.webapp.lora.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Map saveUser(@RequestBody User user){
        userService.saveUser(user);
        return Collections.singletonMap("message","Success");
    }

    @PostMapping("/login")
    public Map login(@RequestBody User user){
        User existUser = userService.findAllByUserNameAndPassword(user.getUserName(), user.getPassword());

        if (existUser != null) {
            return Collections.singletonMap("message", "success login");
        }
        return Collections.singletonMap("message", "check cred");
    }


}

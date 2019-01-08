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

    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Map saveUser(@RequestBody User user){
        logger.info("Request: " + user);
        User userNameExist = userService.findAllByUserName(user.getUserName());

        if (userNameExist != null){
            logger.info("Response register: " + "Korisnik sa ovim imenom postoji, odaberite drugo ime");
            return Collections.singletonMap("message","Korisnik sa ovim imenom postoji, odaberite drugo ime");
        }
        userService.saveUser(user);
        logger.info("Response: " + "Uspešno ste se registrovali");
        return Collections.singletonMap("message","Uspešno ste se registrovali");
    }

    @PostMapping("/login")
    public Map login(@RequestBody User user){
        logger.info("Request login: " + user);
        User existUser = userService.findAllByUserNameAndPassword(user.getUserName(), user.getPassword());

        if (existUser != null) {
            logger.info("Response: " + "Uspešno ste se ulogovali");
            return Collections.singletonMap("message", "Uspešno ste se ulogovali");
        }
        logger.info("Response: " + "Proverite korisničko ime ili šifru");
        return Collections.singletonMap("message", "Proverite korisničko ime ili šifru");
    }
}

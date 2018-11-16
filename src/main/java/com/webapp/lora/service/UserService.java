package com.webapp.lora.service;

import com.webapp.lora.entity.User;
import com.webapp.lora.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User findAllByUserNameAndPassword(String userName, String password){
        return userRepository.findAllByUserNameAndPassword(userName, password);
    }

    public User findAllByUserName(String userName){
        return userRepository.findAllByUserName(userName);
    }

}

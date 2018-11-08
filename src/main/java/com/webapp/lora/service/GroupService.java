package com.webapp.lora.service;

import com.webapp.lora.entity.Group;
import com.webapp.lora.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    public void saveGroup(Group group){
        groupRepository.save(group);
    }

    public List<Group> findAllByUserId(String userId){
        return groupRepository.findAllByUserId(userId);
    }

    public List<Group> findAllByDeviceId(String deviceId){
        return groupRepository.findAllByUserId(deviceId);
    }

}

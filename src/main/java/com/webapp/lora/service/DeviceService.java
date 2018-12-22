package com.webapp.lora.service;

import com.webapp.lora.entity.Device;
import com.webapp.lora.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    DeviceRepository deviceRepository;

    public void addDevice(Device device){
        deviceRepository.save(device);
    }

    public List<Device> findAllByUserId(String userId){
        return deviceRepository.findAllByUserId(userId);
    }


    public Device findByDevEUI(String name){
        return deviceRepository.findByDevEUI(name);}
}

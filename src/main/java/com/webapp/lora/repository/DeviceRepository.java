package com.webapp.lora.repository;

import com.webapp.lora.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findAllByUserId(String userId);

    Device findById(int id);



}

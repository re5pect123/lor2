package com.webapp.lora.repository;

import com.webapp.lora.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>{

    List<Group> findAllByUserId(String userId);

    Group findByName(String name);
}

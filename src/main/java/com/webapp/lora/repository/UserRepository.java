package com.webapp.lora.repository;

import com.webapp.lora.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findAllByUserNameAndPassword(String userName, String password);


}

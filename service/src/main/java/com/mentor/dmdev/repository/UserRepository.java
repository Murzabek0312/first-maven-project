package com.mentor.dmdev.repository;

import com.mentor.dmdev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
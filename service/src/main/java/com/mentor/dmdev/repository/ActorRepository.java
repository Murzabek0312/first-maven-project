package com.mentor.dmdev.repository;

import com.mentor.dmdev.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {

}
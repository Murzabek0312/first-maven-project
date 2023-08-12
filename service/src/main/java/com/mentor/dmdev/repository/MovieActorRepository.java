package com.mentor.dmdev.repository;

import com.mentor.dmdev.entity.MoviesActor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieActorRepository extends JpaRepository<MoviesActor, Long> {

}
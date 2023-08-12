package com.mentor.dmdev.repository;

import com.mentor.dmdev.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
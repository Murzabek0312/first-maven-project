package com.mentor.dmdev.repository;

import com.mentor.dmdev.entity.Actor;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Long> {

    @Query("select ac from Actor ac where ac.id IN (:ids)")
    List<Actor> findAllByIds(@NonNull @Param("ids") Collection<Long> ids);
}
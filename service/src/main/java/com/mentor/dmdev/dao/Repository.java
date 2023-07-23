package com.mentor.dmdev.dao;

import com.mentor.dmdev.entity.BaseEntity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Repository<K extends Serializable, E extends BaseEntity<K>> {

    @NotNull
    E save(@NotNull E entity);

    void delete(@NotNull E entity);

    void update(@NotNull E entity);

    @NotNull
    Optional<E> findById(@NotNull K id);

    @NotNull
    List<E> findAll();
}
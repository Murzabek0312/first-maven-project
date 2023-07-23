package com.mentor.dmdev.dao;

import com.mentor.dmdev.entity.BaseEntity;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class RepositoryBase<K extends Serializable, E extends BaseEntity<K>> implements Repository<K, E> {

    private final Class<E> clazz;
    private final EntityManager entityManager;

    @NotNull
    @Override
    public E save(@NotNull E entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void delete(@NotNull E entity) {
        entityManager.remove(entity);
        entityManager.flush();
    }

    @Override
    public void update(@NotNull E entity) {
        entityManager.merge(entity);

    }

    @NotNull
    @Override
    public Optional<E> findById(@NotNull K id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    @NotNull
    @Override
    public List<E> findAll() {
        var criteria = entityManager.getCriteriaBuilder().createQuery(clazz);
        criteria.from(clazz);
        return entityManager.createQuery(criteria)
                .getResultList();
    }
}
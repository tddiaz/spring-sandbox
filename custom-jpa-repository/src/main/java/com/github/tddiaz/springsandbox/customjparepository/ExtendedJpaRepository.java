package com.github.tddiaz.springsandbox.customjparepository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class ExtendedJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements ExtendedRepository<T, ID> {

    private EntityManager entityManager;

    public ExtendedJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    @Transactional
    public <S extends T> S persist(S entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public <S extends T> S update(S entity) {
        return entityManager.merge(entity);
    }
}

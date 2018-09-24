package com.github.tddiaz.springsandbox.customjparepository;

import javax.persistence.EntityManager;

public class AbstractCustomRepository<E> implements CustomRepository<E> {

    private final EntityManager entityManager;

    public AbstractCustomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public <S extends E> S persist(S entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public <S extends E> S update(S entity) {
        return entityManager.merge(entity);
    }
}

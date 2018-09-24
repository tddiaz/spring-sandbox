package com.github.tddiaz.springsandbox.customjparepository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class DepartmentJpaRepositoryCustomImpl extends AbstractCustomRepository<Department> implements DepartmentJpaRepositoryCustom {

    @PersistenceContext
    private final EntityManager entityManager;

    public DepartmentJpaRepositoryCustomImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }
}

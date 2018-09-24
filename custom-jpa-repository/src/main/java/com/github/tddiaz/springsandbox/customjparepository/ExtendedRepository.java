package com.github.tddiaz.springsandbox.customjparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface ExtendedRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    <S extends T> S persist(S entity);
    <S extends T> S update(S entity);
}

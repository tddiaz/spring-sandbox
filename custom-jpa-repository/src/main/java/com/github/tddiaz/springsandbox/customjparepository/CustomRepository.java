package com.github.tddiaz.springsandbox.customjparepository;

public interface CustomRepository<E> {
    <S extends E> S persist(S entity);
    <S extends E> S update(S entity);
}

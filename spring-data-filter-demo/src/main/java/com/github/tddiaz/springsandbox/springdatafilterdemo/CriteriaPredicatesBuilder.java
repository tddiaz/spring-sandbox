package com.progressoft.corpay.incominglg.infrastructure.repository.search;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

/**
 * @author Tristan Diaz
 * @param <E> Entity Rot
 */
public final class CriteriaPredicatesBuilder<E> {

    private List<Predicate> predicates;
    private Root<E> root;
    private CriteriaBuilder cb;
    private Join<?,?> join;

    private CriteriaPredicatesBuilder(Root<E> root, CriteriaBuilder cb, Join<?,?> join) {
        this.root = root;
        this.cb = cb;
        this.join = join;
        this.predicates = new ArrayList<>();
    }

    public static <E> CriteriaPredicatesBuilder<E> predicates(Root<E> root, CriteriaBuilder criteriaBuilder, Join<?,?> join) {
        return new CriteriaPredicatesBuilder<>(root, criteriaBuilder, join);
    }

    public CriteriaPredicatesBuilder<E> like(String path, String value) {
        _like(root.get(path), value);
        return this;
    }

    public CriteriaPredicatesBuilder<E> likeFromJoin(String path, String value) {
        _like(join.get(path), value);
        return this;
    }

    public CriteriaPredicatesBuilder<E> equal(String path, Object value) {
        _equal(root.get(path), value);
        return this;
    }

    public CriteriaPredicatesBuilder<E> equal(String path, String value, Function<String, ?> converter) {
        _equal(root.get(path), StringUtils.isNotBlank(value) ? converter.apply(value) : null);
        return this;
    }

    public CriteriaPredicatesBuilder<E> equalFromJoin(String path, String value, Function<String, ?> converter) {
        _equal(join.get(path), StringUtils.isNotBlank(value) ? converter.apply(value) : null);
        return this;
    }

    public CriteriaPredicatesBuilder<E> withinDay(String path, String localDate) {
        _withinDay(root.get(path), localDate);
        return this;
    }

    private void _like(Expression<String> expression, String value) {
        if (StringUtils.isNotBlank(value)) {
            this.predicates.add(cb.like(expression, value));
        }
    }

    private void _equal(Expression<?> expression, Object value) {
        if (Objects.nonNull(value)) {
            this.predicates.add(cb.equal(expression, value));
        }
    }

    private void _withinDay(Expression expression, String date) {
        if (StringUtils.isNotBlank(date)) {
            final LocalDate localDate = LocalDate.parse(date, ISO_LOCAL_DATE);
            final LocalDateTime startOfDay = localDate.atStartOfDay();
            final LocalDateTime beforeNextDay = LocalDateTime.of(localDate, LocalTime.MAX);

            this.predicates.add(cb.between(expression, startOfDay, beforeNextDay));
        }
    }

    public List<Predicate> build() {
        return this.predicates;
    }
}
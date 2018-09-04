package com.github.tddiaz.springsandbox.springdatafilterdemo;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Component
@AllArgsConstructor
public class StudentRepository {

    private final StudentJpaRepository jpaRepository;

    public List<Student> loadStudents(SearchCriteria searchCriteria) {
      return jpaRepository.findAll(createSearchSpecificationByCriteria(searchCriteria));
    }

    @Repository
    public interface StudentJpaRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    }

    private static Specification<Student> createSearchSpecificationByCriteria(SearchCriteria searchCriteria) {

        return (Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

            final List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(searchCriteria.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), searchCriteria.getName()));
            }

            if (Objects.nonNull(searchCriteria.getBirthDate())) {
                predicates.add(criteriaBuilder.equal(root.get("birthDate"), searchCriteria.getBirthDate()));
            }

            if (Objects.nonNull(searchCriteria.getLevel())) {
                predicates.add(criteriaBuilder.equal(root.get("level"), searchCriteria.getLevel()));
            }

            if (Objects.nonNull(searchCriteria.getAge())) {
                predicates.add(criteriaBuilder.equal(root.get("age"), searchCriteria.getAge()));
            }

            if (Objects.nonNull(searchCriteria.getAgeRange())) {
                SearchCriteria.AgeRange ageRange = searchCriteria.getAgeRange();
                predicates.add(criteriaBuilder.between(root.get("age"), ageRange.getMin(), ageRange.getMax()));
            }

            Predicate predicate = criteriaBuilder.disjunction();
            predicate.getExpressions().addAll(predicates);

            query.where(predicate);

            return query.getRestriction();
        };
    }
}

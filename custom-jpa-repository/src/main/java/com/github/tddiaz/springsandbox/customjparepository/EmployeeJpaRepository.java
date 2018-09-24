package com.github.tddiaz.springsandbox.customjparepository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {
}

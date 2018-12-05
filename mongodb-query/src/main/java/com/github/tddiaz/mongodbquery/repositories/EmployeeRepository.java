package com.github.tddiaz.mongodbquery.repositories;

import com.github.tddiaz.mongodbquery.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
}

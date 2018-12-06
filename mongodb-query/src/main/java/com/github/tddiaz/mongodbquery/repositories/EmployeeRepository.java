package com.github.tddiaz.mongodbquery.repositories;

import com.github.tddiaz.mongodbquery.domain.Employee;
import com.github.tddiaz.mongodbquery.domain.EmployeeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<EmployeeEntity, String> {
}

package com.github.tddiaz.mongodbquery.repositories;

import com.github.tddiaz.mongodbquery.domain.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<Department, String> {
}

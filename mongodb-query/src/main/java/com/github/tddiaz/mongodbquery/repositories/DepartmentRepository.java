package com.github.tddiaz.mongodbquery.repositories;

import com.github.tddiaz.mongodbquery.domain.Department;
import com.github.tddiaz.mongodbquery.domain.DepartmentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<DepartmentEntity, String> {
}

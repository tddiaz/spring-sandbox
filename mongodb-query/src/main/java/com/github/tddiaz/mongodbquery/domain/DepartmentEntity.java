package com.github.tddiaz.mongodbquery.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "departments")
public class DepartmentEntity {

    @Id
    private String id;

    private Department department;

    public DepartmentEntity(Department department) {
        this.department = department;
    }
}

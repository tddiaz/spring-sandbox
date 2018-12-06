package com.github.tddiaz.mongodbquery.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employees")
@Data
public class EmployeeEntity {

    @Id
    private String id;

    private Employee employee;

    public EmployeeEntity(Employee employee) {
        this.employee = employee;
    }
}

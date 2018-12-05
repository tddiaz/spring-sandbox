package com.github.tddiaz.mongodbquery.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Employee {

    @Id
    private String id;
    private String name;
    private int age;
    private String departmentId;

    public Employee(String name, int age, String departmentId) {
        this.name = name;
        this.age = age;
        this.departmentId = departmentId;
    }
}

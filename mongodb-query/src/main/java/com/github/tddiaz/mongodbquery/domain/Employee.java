package com.github.tddiaz.mongodbquery.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class Employee {

    private String ref;
    private String name;
    private int age;
    private String deptRef;

    public Employee(String ref, String name, int age, String deptRef) {
        this.name = name;
        this.age = age;
        this.deptRef = deptRef;
        this.ref = ref;
    }
}

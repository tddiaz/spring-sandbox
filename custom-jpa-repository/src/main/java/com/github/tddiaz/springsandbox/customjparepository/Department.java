package com.github.tddiaz.springsandbox.customjparepository;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departmentId")
    private List<Employee> employees;

    public void addStudent(Employee employee) {
        if (Objects.isNull(employees)) {
            employees = new ArrayList<>();
        }

        employees.add(employee);
    }
}


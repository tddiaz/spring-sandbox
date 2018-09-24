package com.github.tddiaz.springsandbox.customjparepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DepartmentService {

    private final DepartmentJpaRepository departmentJpaRepository;

    @Transactional
    public Long create() {

//        return departmentJpaRepository.
    }

}

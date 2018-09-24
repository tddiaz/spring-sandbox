package com.github.tddiaz.springsandbox.springdatafilterdemo;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by user on 9/2/18.
 */
@RestController
@AllArgsConstructor
public class Controller {

    private final StudentRepository studentRepository;

    @GetMapping("/employees")
    public ResponseEntity<List<Student>> findAll(@RequestBody SearchCriteria searchCriteria) {
        return ResponseEntity.ok(studentRepository.loadStudents(searchCriteria));
    }
}

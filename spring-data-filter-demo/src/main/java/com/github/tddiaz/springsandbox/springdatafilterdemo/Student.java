package com.github.tddiaz.springsandbox.springdatafilterdemo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by user on 9/2/18.
 */
@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private LocalDate birthDate;
    private int age;
    @Enumerated(value = EnumType.STRING)
    private Level level;

    public Student() {}

    public Student(String name, LocalDate birthDate, int age, Level level) {
        this.name = name;
        this.birthDate = birthDate;
        this.age = age;
        this.level = level;
    }

    public enum Level {
        FIRST, SECOND, THIRD
    }
}

package com.github.tddiaz.springsandbox.springdatafilterdemo;

import com.github.tddiaz.springsandbox.springdatafilterdemo.Student.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Created by user on 9/3/18.
 */
@Getter
@Setter
public class SearchCriteria {

    private String name;
    private LocalDate birthDate;
    private int age;
    private Level level;
    private AgeRange ageRange;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static class AgeRange {
        private int min;
        private int max;
    }
}

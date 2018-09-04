package com.github.tddiaz.springsandbox.springdatafilterdemo;

import com.google.common.collect.Lists;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.github.tddiaz.springsandbox.springdatafilterdemo.Student.Level.FIRST;
import static com.github.tddiaz.springsandbox.springdatafilterdemo.Student.Level.SECOND;
import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by user on 9/3/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(classes = MainApplication.class)
@Slf4j
public class WebIntegrationTest {

    @LocalServerPort
    private int serverPort;

    @Autowired
    private StudentRepository.StudentJpaRepository studentJpaRepository;

    @Before
    public void setup() {
        List<Student> students = Lists.newArrayList(
                new Student("tristan diaz", LocalDate.of(1992, 10, 6), 26, FIRST),
                new Student("barry allen", LocalDate.of(2000, 10, 6), 30, Student.Level.SECOND),
                new Student("bart allen", LocalDate.of(1992, 10, 6), 15, Student.Level.SECOND),
                new Student("iris west allen", LocalDate.of(1992, 10, 6), 29, Student.Level.THIRD),
                new Student("cisco ramon", LocalDate.of(1992, 10, 6), 35, FIRST),
                new Student("caitlin snow", LocalDate.of(1992, 10, 6), 26, Student.Level.SECOND),
                new Student("joe west", LocalDate.of(1992, 10, 6), 50, Student.Level.THIRD),
                new Student("wally west", LocalDate.of(1992, 10, 6), 40, FIRST));

        studentJpaRepository.saveAll(students);
    }

    @Test
    public void testNameFilter() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setAge(26);
        searchCriteria.setAgeRange(new SearchCriteria.AgeRange(25,30));
        searchCriteria.setLevel(SECOND);

        given()
            .log()
            .all()
            .port(serverPort)
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(searchCriteria)
        .when()
            .get("/students")
        .then()
            .statusCode(200);
    }
}

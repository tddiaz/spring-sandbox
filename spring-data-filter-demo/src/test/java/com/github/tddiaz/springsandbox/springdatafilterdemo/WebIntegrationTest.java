package com.github.tddiaz.springsandbox.springdatafilterdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static com.github.tddiaz.springsandbox.springdatafilterdemo.Student.Level.FIRST;
import static com.github.tddiaz.springsandbox.springdatafilterdemo.Student.Level.SECOND;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
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

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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
    public void testFilters() throws Exception {

        {   // test filter by name
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setName("west");

            Response response = given()
                    .log()
                    .all()
                    .port(serverPort)
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(searchCriteria)
                    .when()
                    .get("/employees").andReturn();

            List<Student> students = OBJECT_MAPPER.readValue(response.getBody().asString(), List.class);
            assertThat(students).hasSize(3);
            assertThat(students).extracting("name").contains("iris west allen", "joe west", "wally west");
        }

        { // test filter for level
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setLevel(Student.Level.THIRD);

            Response response = given()
                    .log()
                    .all()
                    .port(serverPort)
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(searchCriteria)
                    .when()
                    .get("/employees").andReturn();

            List<Student> students = OBJECT_MAPPER.readValue(response.getBody().asString(), List.class);
            assertThat(students).hasSize(2);
            assertThat(students).extracting("name").contains("iris west allen", "joe west");
        }

        { // for birthdate
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setBirthDate(LocalDate.of(1992, 10, 6));

            Response response = given()
                    .log()
                    .all()
                    .port(serverPort)
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(searchCriteria)
                    .when()
                    .get("/employees").andReturn();

            List<Student> students = OBJECT_MAPPER.readValue(response.getBody().asString(), List.class);
            assertThat(students).hasSize(7);
        }

        { // for age
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setAge(30);

            Response response = given()
                    .log()
                    .all()
                    .port(serverPort)
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(searchCriteria)
                    .when()
                    .get("/employees").andReturn();

            List<Student> students = OBJECT_MAPPER.readValue(response.getBody().asString(), List.class);
            assertThat(students).hasSize(1);
            assertThat(students).extracting("name").contains("barry allen");
        }

        { // age range
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setAgeRange(new SearchCriteria.AgeRange(25, 30));

            Response response = given()
                    .log()
                    .all()
                    .port(serverPort)
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(searchCriteria)
                    .when()
                    .get("/employees").andReturn();

            List<Student> students = OBJECT_MAPPER.readValue(response.getBody().asString(), List.class);
            assertThat(students).hasSize(4);
            assertThat(students).extracting("name").contains("barry allen", "tristan diaz", "caitlin snow", "iris west allen");
        }

        { // bday and level
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setBirthDate(LocalDate.of(1992, 10, 6));
            searchCriteria.setLevel(SECOND);

            Response response = given()
                    .log()
                    .all()
                    .port(serverPort)
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(searchCriteria)
                    .when()
                    .get("/employees").andReturn();

            List<Student> students = OBJECT_MAPPER.readValue(response.getBody().asString(), List.class);
            assertThat(students).hasSize(2);
            assertThat(students).extracting("name").contains("bart allen","caitlin snow");
        }

        { // no match
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setName("ramon");
            searchCriteria.setBirthDate(LocalDate.of(1992, 10, 6));
            searchCriteria.setLevel(SECOND);

            Response response = given()
                    .log()
                    .all()
                    .port(serverPort)
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(searchCriteria)
                    .when()
                    .get("/employees").andReturn();

            List<Student> students = OBJECT_MAPPER.readValue(response.getBody().asString(), List.class);
            assertThat(students).isEmpty();
        }

        { // combination of criteria
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setName("ramon");
            searchCriteria.setBirthDate(LocalDate.of(1992, 10, 6));
            searchCriteria.setLevel(FIRST);
            searchCriteria.setAge(35);

            Response response = given()
                    .log()
                    .all()
                    .port(serverPort)
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(searchCriteria)
                    .when()
                    .get("/employees").andReturn();

            List<Student> students = OBJECT_MAPPER.readValue(response.getBody().asString(), List.class);
            assertThat(students).hasSize(1);
        }
    }
}

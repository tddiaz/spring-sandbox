package com.github.tddiaz.mongodbquery.repositories;

import com.github.tddiaz.mongodbquery.domain.Department;
import com.github.tddiaz.mongodbquery.domain.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@DataMongoTest
public class EmployeeRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void setup() {

        Department departmentX = departmentRepository.save(new Department(Department.Type.X));
        Department departmentY = departmentRepository.save(new Department(Department.Type.Y));

        departmentRepository.saveAll(Arrays.asList(departmentX, departmentY));

        log.info("department count: {}", departmentRepository.count());

        employeeRepository.save(new Employee("barry", 25, departmentX.getId()));
        employeeRepository.save(new Employee("cisco", 25, departmentX.getId()));
        employeeRepository.save(new Employee("caitlin", 25, departmentX.getId()));
        employeeRepository.save(new Employee("harrison", 40, departmentY.getId()));

        log.info("employee count: {}", employeeRepository.count());

    }

    @Test
    public void testQueryByPropertyLessThan() {

        Query query = new Query();
        query.addCriteria(Criteria.where("age").lt(30));

        List<Employee> employees = mongoTemplate.find(query, Employee.class);
        assertThat(employees.size(), is(3));

    }

    @Test
    public void testQueryByPropertyGreaterThan() {

        Query query = new Query();
        query.addCriteria(Criteria.where("age").gt(30));

        List<Employee> employees = mongoTemplate.find(query, Employee.class);
        assertThat(employees.size(), is(1));
    }

    @Test
    public void testQueryByPropertyEquals() {

        Query query = new Query();
        query.addCriteria(Criteria.where("age").is(25));

        List<Employee> employees = mongoTemplate.find(query, Employee.class);
        assertThat(employees.size(), is(3));
    }

    @Test
    public void testQueryByMultipleCriteria() {

        Query query = new Query();
        query.addCriteria(Criteria.where("age").is(25));
        query.addCriteria(Criteria.where("name").is("barry"));

        List<Employee> employees = mongoTemplate.find(query, Employee.class);
        assertThat(employees.size(), is(1));
    }

    @Test
    public void testQueryAggregation() {
        // TODO
    }


}
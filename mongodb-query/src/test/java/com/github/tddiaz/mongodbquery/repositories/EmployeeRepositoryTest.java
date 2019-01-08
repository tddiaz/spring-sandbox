package com.github.tddiaz.mongodbquery.repositories;

import com.github.tddiaz.mongodbquery.domain.Department;
import com.github.tddiaz.mongodbquery.domain.DepartmentEntity;
import com.github.tddiaz.mongodbquery.domain.Employee;
import com.github.tddiaz.mongodbquery.domain.EmployeeEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
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

        DepartmentEntity departmentX = departmentRepository.save(new DepartmentEntity(new Department("1", Department.Type.X)));
        DepartmentEntity departmentY = departmentRepository.save(new DepartmentEntity(new Department("2", Department.Type.Y)));

        log.info("department count: {}", departmentRepository.count());

        employeeRepository.save(new EmployeeEntity(new Employee("1", "barry", 25, departmentX.getDepartment().getRef())));
        employeeRepository.save(new EmployeeEntity(new Employee("2", "cisco", 25, departmentX.getDepartment().getRef())));
        employeeRepository.save(new EmployeeEntity(new Employee("3", "caitlin", 25, departmentX.getDepartment().getRef())));
        employeeRepository.save(new EmployeeEntity(new Employee("4", "harrison", 40, departmentY.getDepartment().getRef())));

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
    public void testFindEmployeesWithinDepartmentTypeX() {

        LookupOperation lookupOperation = LookupOperation.newLookup().
                from("departments").
                localField("employee.deptRef").
                foreignField("department.ref").
                as("dprt");

        // sort_by='status' -> 'lg.status'
        AggregationOperation departmentIdMatchOperation = Aggregation.match(Criteria.where("dprt.department.type").is("X"));
        ProjectionOperation projectionOperation = new ProjectionOperation().andExclude("dprt");

        Aggregation aggregation = Aggregation.newAggregation(lookupOperation, departmentIdMatchOperation, projectionOperation);

        List<EmployeeEntity> employees = mongoTemplate.aggregate(aggregation, "employees", EmployeeEntity.class).getMappedResults();

        assertEquals(3, employees.size());
    }

    @Test
    public void testFindEmployeeWithNameWithinDepartmentTypeX() {

        AggregationOperation employeeNameOperation = Aggregation.match(Criteria.where("employee.name").is("barry"));

        LookupOperation lookupOperation = LookupOperation.newLookup().
                from("departments").
                localField("employee.deptRef").
                foreignField("department.ref").
                as("dprt");

        AggregationOperation departmentIdMatchOperation = Aggregation.match(Criteria.where("dprt.department.type").is("X"));
        ProjectionOperation projectionOperation = new ProjectionOperation().andExclude("dprt");

        Aggregation aggregation = Aggregation.newAggregation(employeeNameOperation, lookupOperation, departmentIdMatchOperation, projectionOperation);

        List<EmployeeEntity> employees = mongoTemplate.aggregate(aggregation, "employees", EmployeeEntity.class).getMappedResults();

        assertEquals(1, employees.size());
    }

    @Test
    public void testFindEmployeesWithLessThanAgeWithinDepartmentTypeX() {

        AggregationOperation employeeAgeOperation = Aggregation.match(Criteria.where("employee.age").lt(40));

//        LookupOperation lookupOperation = LookupOperation.newLookup().
//                from("departments").
//                localField("employee.deptRef").
//                foreignField("department.ref").
//                as("dprt");
//
//        AggregationOperation departmentIdMatchOperation = Aggregation.match(Criteria.where("dprt.department.type").is("X"));
//        ProjectionOperation projectionOperation = new ProjectionOperation().andExclude("dprt");
//
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(employeeAgeOperation);
        aggregationOperations.addAll(aggerateForDepartmentType());

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);

        List<EmployeeEntity> employees = mongoTemplate.aggregate(aggregation, "employees", EmployeeEntity.class).getMappedResults();

        assertEquals(3, employees.size());
    }

    public void testRequest(Map<String, String> requests) {

        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        requests.forEach((key, value) -> {
            aggregationOperations.addAll(LgRequestParam.OPS_MAP.get(key));
        });

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    }




    private List<AggregationOperation> aggerateForDepartmentType() {

        LookupOperation lookupOperation = LookupOperation.newLookup().
                from("departments").
                localField("employee.deptRef").
                foreignField("department.ref").
                as("dprt");
        AggregationOperation departmentIdMatchOperation = Aggregation.match(Criteria.where("dprt.department.type").is("X"));
        ProjectionOperation projectionOperation = new ProjectionOperation().andExclude("dprt");

        return Arrays.asList(lookupOperation, departmentIdMatchOperation, projectionOperation);
    }


}
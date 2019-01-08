package com.github.tddiaz.mongodbquery.repositories;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class LgRequestParam {
        
    public static final Map<String, List<AggregationOperation>> OPS_MAP;

    static {
        OPS_MAP = new HashMap<>();
        OPS_MAP.put("workflow_status", aggerateForDepartmentType());
        OPS_MAP.put("lg_status", matchOperationForLgStatus());
    }

    private static List<AggregationOperation> matchOperationForLgStatus() {

        return Arrays.asList(Aggregation.match(Criteria.where("lg.status").is("X")));
    }

    private static List<AggregationOperation> aggerateForDepartmentType() {

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
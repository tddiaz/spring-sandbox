package com.github.tddiaz.mongodbquery.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Department {

    @Id
    private String id;

    private Type type;

    public Department(Type type) {
        this.type = type;
    }

    public enum Type {
        X,Y
    }
}

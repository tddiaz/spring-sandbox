package com.github.tddiaz.mongodbquery.domain;

import lombok.Data;

@Data
public class Department {

    private String ref;
    private Type type;

    public Department(String ref, Type type) {
        this.ref = ref;
        this.type = type;
    }

    public enum Type {
        X,Y
    }
}

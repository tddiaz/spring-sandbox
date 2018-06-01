package com.github.tristan.mongodbschemademo.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class User extends AbstractDomain {

    private String name;

    @DBRef(lazy = true)
    private List<Comment> comments;

}

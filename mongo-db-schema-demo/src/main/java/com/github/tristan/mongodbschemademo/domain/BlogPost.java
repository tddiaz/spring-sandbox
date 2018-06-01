package com.github.tristan.mongodbschemademo.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class BlogPost {

    private String title;

    @DBRef
    private List<Comment> comments;
}

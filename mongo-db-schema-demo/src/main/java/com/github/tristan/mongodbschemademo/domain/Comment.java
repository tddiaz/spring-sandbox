package com.github.tristan.mongodbschemademo.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Comment extends AbstractDomain {

    private String blogPostId;

    @Indexed(unique = true)
    private String userId;

    private String userName;

    private String content;
}

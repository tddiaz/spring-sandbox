package com.github.tristan.mongodbschemademo.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public abstract class AbstractDomain {

    @Id
    private String id;

}

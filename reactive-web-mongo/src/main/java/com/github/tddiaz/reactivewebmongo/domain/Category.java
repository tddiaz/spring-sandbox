package com.github.tddiaz.reactivewebmongo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
public class Category {

    @Id
    private String id;
    private String name;

    public Category(String name) {
        this.name = name;
    }

}

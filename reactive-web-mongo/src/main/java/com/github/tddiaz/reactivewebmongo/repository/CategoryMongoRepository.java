package com.github.tddiaz.reactivewebmongo.repository;

import com.github.tddiaz.reactivewebmongo.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryMongoRepository extends ReactiveMongoRepository<Category, String> {
}

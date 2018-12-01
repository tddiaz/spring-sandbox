package com.github.tddiaz.reactivewebmongo.controller;

import com.github.tddiaz.reactivewebmongo.domain.Category;
import com.github.tddiaz.reactivewebmongo.repository.CategoryMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryMongoRepository categoryMongoRepository;

    public CategoryController(CategoryMongoRepository categoryMongoRepository) {
        this.categoryMongoRepository = categoryMongoRepository;
    }

    @GetMapping
    public Flux<Category> getAll() {
        log.info("get all request..");
        return categoryMongoRepository.findAll();
    }

    @GetMapping("/{categoryId}")
    public Mono<Category> get(@PathVariable  String categoryId) {
        log.info("get category with id: {}", categoryId);
        return categoryMongoRepository.findById(categoryId);
    }

}

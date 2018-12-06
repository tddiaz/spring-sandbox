package com.github.tddiaz.reactivewebmongo.controller;

import com.github.tddiaz.reactivewebmongo.domain.Category;
import com.github.tddiaz.reactivewebmongo.repository.CategoryMongoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@WebFluxTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CategoryMongoRepository categoryMongoRepository;

    @Test
    public void givenListOfCategories_whenGetAll_shouldReturnCategories() {

        when(categoryMongoRepository.findAll()).thenReturn(Flux.just(new Category("test1"), new Category("test2")));

        webTestClient.get()
                .uri("/categories")
                .exchange().expectStatus().isOk()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void givenCategoryId_whenGet_shouldReturnCategory() {

        when(categoryMongoRepository.findById(anyString())).thenReturn(Mono.just(new Category("test1")));

        webTestClient.get()
                .uri("/categories/test-depRef")
                .exchange().expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name", equalTo("test1"));
    }
}
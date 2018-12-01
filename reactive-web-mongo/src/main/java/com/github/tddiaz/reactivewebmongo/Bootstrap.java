package com.github.tddiaz.reactivewebmongo;

import com.github.tddiaz.reactivewebmongo.domain.Category;
import com.github.tddiaz.reactivewebmongo.repository.CategoryMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryMongoRepository categoryMongoRepository;

    public Bootstrap(CategoryMongoRepository categoryMongoRepository) {
        this.categoryMongoRepository = categoryMongoRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (categoryMongoRepository.count().block() == 0) {
            categoryMongoRepository.save(new Category("foods")).block();
            categoryMongoRepository.save(new Category("clothes")).block();
            categoryMongoRepository.save(new Category("appliances")).block();
        }

        log.info("categories count: {}", categoryMongoRepository.count().block());
    }
}

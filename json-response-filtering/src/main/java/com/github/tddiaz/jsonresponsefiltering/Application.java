package com.github.tddiaz.jsonresponsefiltering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.Data;
import lombok.NonNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@RestController
	class Controller {

		@GetMapping("/filter")
		public ResponseEntity filter() {
			final Response response = new Response("value1", "value2", "value3");

			//ignore field3; will only return values of field1 and field2
			final SimpleBeanPropertyFilter beanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");

			final FilterProvider filterProvider = new SimpleFilterProvider().addFilter("responseFilter", beanPropertyFilter);

			final MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(response);
			mappingJacksonValue.setFilters(filterProvider);

			return ResponseEntity.ok(mappingJacksonValue);
		}
	}

	@JsonFilter("responseFilter")
	@Data
	class Response {
		@NonNull
		private String field1, field2, field3;
	}
}

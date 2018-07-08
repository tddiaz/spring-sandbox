package com.github.tddiaz.rabbitmqdemo;

import com.github.tddiaz.rabbitmqdemo.application.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class RabbitmqDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqDemoApplication.class, args);
	}

	@Component
	class Runner implements CommandLineRunner {

		@Autowired
		private EventPublisher eventPublisher;

		@Override
		public void run(String... strings) throws Exception {
			eventPublisher.publishMessage("Hello World");
		}
	}
}

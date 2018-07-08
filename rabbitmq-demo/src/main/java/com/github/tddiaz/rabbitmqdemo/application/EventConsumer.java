package com.github.tddiaz.rabbitmqdemo.application;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * Created by tristan on 7/9/18.
 */
@Component
public class EventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventConsumer.class);

    @RabbitListener(queues = "eventQueue")
    public void consume(String payload) {
        LOGGER.info(payload);
    }
}

package com.github.tddiaz.rabbitmqdemo.application;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by tristan on 7/9/18.
 */
@Component
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange topicExchange;

    public EventPublisher(RabbitTemplate rabbitTemplate, TopicExchange topicExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.topicExchange = topicExchange;
    }


    public void publishMessage(String message) {
        rabbitTemplate.convertAndSend(topicExchange.getName(), "routing.key", message);
    }


}

package edu.dzyachenka.processor.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class RabbitMQListener {
    private static final Logger LOG = LoggerFactory.getLogger(RabbitMQListener.class);

    @RabbitListener(queues = "resourceQueue")
    public void processMessages(final String message) {
        LOG.info("Received message: {}", message);
    }
}

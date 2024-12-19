package org.example.persons.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableRabbit
public class RabbitConfiguration {

    @Bean
    public Queue localSpringQueue(){
        return new Queue("spring-queue");
    }

    @Bean
    public Queue noteQueue(){
        return new Queue("note-queue");
    }
    @Bean
    public Exchange messageExchangeFanout(){
        return new FanoutExchange("message-fanout");
    }

    @Bean
    public Exchange noteExchangeFanout(){
        return new FanoutExchange("note-fanout");
    }


    @Bean
    public Binding springQueueBinding(){
        return BindingBuilder
                .bind(localSpringQueue())
                .to(messageExchangeFanout())
                .with("")
                .noargs();
    }    @Bean
    public Binding noteQueueBinding(){
        return BindingBuilder
                .bind(noteQueue())
                .to(noteExchangeFanout())
                .with("")
                .noargs();
    }
}

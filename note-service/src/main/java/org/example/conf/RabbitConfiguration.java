package org.example.conf;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.example.event.PersonUpdatedEvent;
import org.example.service.PersonClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class RabbitConfiguration {

    private final PersonClientService personClientService;

    @Bean
    public Consumer<PersonUpdatedEvent> personUpdatedEventConsumer(){
        return event -> {
            log.info("Received an event: {}", event);
            personClientService.synchronizePersonById(event.personId());
        };
    }
}

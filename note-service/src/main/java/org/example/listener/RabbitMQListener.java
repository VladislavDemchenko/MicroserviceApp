package org.example.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.NoteService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQListener {

    private final NoteService noteService;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveMessage(Long personId) {
        log.info("Received request to delete notes for person with id: {}", personId);
        noteService.deleteNotesByPersonId(personId);
        log.info("Notes deleted for person with id: {}", personId);
    }
}

package org.example.persons.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NoteListener {

    @RabbitListener(queues = "note-queue")
    public void getNote(String note){
        System.out.println(note);
    }
}

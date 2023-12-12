package ru.enedinae.notes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.enedinae.notes.repository.NoteRepository;

@Component
public class CheckDeadline extends Thread {
    private final NoteRepository repository;
    @Autowired
    public CheckDeadline(NoteRepository repository) {
        this.repository = repository;
    }
    @Override
    public void run() {
        try {
            while (true) {
                repository.checkDeadline();
                Thread.sleep(60000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

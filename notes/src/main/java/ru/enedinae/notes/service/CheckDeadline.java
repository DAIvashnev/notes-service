package ru.enedinae.notes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.enedinae.notes.repository.NoteRepository;

@Component
public class CheckDeadline extends Thread implements CommandLineRunner {
    private NoteRepository repository;
    @Autowired
    public CheckDeadline(NoteRepository repository) {
        this.repository = repository;
    }
    @Override
    public void run() {
        try {
            while (true) {
                repository.checkDeadline();
                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        start();
    }
}

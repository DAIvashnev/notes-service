package ru.enedinae.notes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.enedinae.notes.repository.NoteRepository;
import ru.enedinae.notes.service.BusinessScheduler;

@Component
public class BusinessSchedulerImpl implements BusinessScheduler {
    private NoteRepository repository;
    @Autowired
    public BusinessSchedulerImpl(NoteRepository repository) {
        this.repository = repository;
    }

    @Override
    @Scheduled(initialDelayString = "${scheduler.initialIntervalMs}", fixedDelayString = "${scheduler.fixedIntervalMs}")
    public void checkDeadline() {
        repository.checkDeadline();
    }
}

package ru.enedinae.notes.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ru.enedinae.notes.db.DataBaseManager;
import ru.enedinae.notes.mapper.NoteMapper;
import ru.enedinae.notes.repository.impl.NoteRepositoryImpl;
import ru.enedinae.notes.ui.impl.CommandLineUiImpl;

public class Application {
    public static void main(String[] args)  {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.getBean("thread", CheckDeadline.class).start();
        context.getBean("ui", CommandLineUiImpl.class).start();
        context.close();
    }

    @Component("thread")
    private static class CheckDeadline extends Thread {
        NoteRepositoryImpl repository = new NoteRepositoryImpl(new DataBaseManager(), new NoteMapper());
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
}
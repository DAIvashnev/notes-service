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
        context.getBean("ui", CommandLineUiImpl.class).start();
        context.close();
    }
}
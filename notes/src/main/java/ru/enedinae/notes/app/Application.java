package ru.enedinae.notes.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.enedinae.notes.service.impl.CheckDeadline;
import ru.enedinae.notes.ui.impl.CommandLineUiImpl;

public class Application {
    public static void main(String[] args)  {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.getBean("checkDeadline", CheckDeadline.class).start();
        context.getBean("ui", CommandLineUiImpl.class).start();
        context.close();
    }
}
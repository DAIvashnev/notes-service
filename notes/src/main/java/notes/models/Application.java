package notes.models;

import java.util.List;
import java.util.Optional;

import static notes.models.NoteStatus.*;

public class Application {
    //private UserInterface ui;
    /*static List<Note> list = List.of(new Note("rew","rew","rew",NEW),
            new Note("rew","rew","rew",CLOSED),
            new Note("rew","rew","rew",NEW),
            new Note("rew","rew","rew",EEE),
            new Note("rew","rew","rew",QQQ));*/
    public static void main(String[] args)  {
        new CommandLineUiImpl().start();
       /* System.out.println(deleteNoteById(6));
        System.out.println(list);
        System.out.println(deleteNoteById(3));
        System.out.println(list);*/
    }

    /*public static boolean deleteNoteById(Integer id) {
        if(list.stream().filter(e->e.getId().equals(id)).findFirst().isPresent()) {

        }

        return list.stream().filter(e->e.getId().equals(id)).findFirst().isPresent();
    }*/
}



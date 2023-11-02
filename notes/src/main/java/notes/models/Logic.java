package notes.models;

import java.util.List;

import static notes.models.NoteStatus.*;

public class Logic {
    //static Logic logic = new Logic();
    /*static Menu menu = new Menu();

    public void createNote(List<Note> notes) throws InterruptedException {
        System.out.print("Имя вашей заметки: ");
        String name = Application.scanner.nextLine();
        System.out.print("Содержание: ");
        String infoNote = Application.scanner.nextLine();
        System.out.print("Срок выполнения: ");
        String deadline = Application.scanner.nextLine();
        notes.add(new Note(name,infoNote,deadline,NEW));
        System.out.printf("\nЗаметка успешно создана! id заметки - %s", Application.note.get(Application.note.size()-1).getId());
        //menu.choiceMenu();
    }

    public void deleteNote() throws InterruptedException {
        System.out.println("Вот список ваших заметок. Введите имя заметки для удаления?");
        Application.note.forEach(note -> System.out.println(note.getName()));
        String name = Application.scanner.nextLine();
        if(Application.note.removeIf(note -> note.getName().equals(name))) {
            System.out.println("Заметка успешно удалена.");
            menu.choiceMenu();
            return;
        }
        System.out.println("Заметка с таким именем не существует.");
        //Menu.yesOrNo(LIST_MENU);
    }

    public void updateNote() throws InterruptedException {
        String data;
        System.out.println("Вот список ваших заметок. Введите имя заметки которую хотите изменить?");
        Application.note.forEach(note -> System.out.println(note.getName()));
        String name = Application.scanner.nextLine();
        for(Note note : Application.note) {
            if (note.getName().equals(name)) {
                System.out.println("Выберите, что хотите изменить:\n1 - Имя\n2 - Заметка выполнена\n3 - Содержание\n4 - Срок выполнния\n5 - Отменить");
                data = Application.scanner.nextLine();
                switch (data) {
                    case "1" :
                        System.out.println("Введите новое имя: ");
                        data = Application.scanner.nextLine();
                        note.setName(data); break;
                    case "2" :
                        note.setStatus(CLOSED); break;
                    case "3" :
                        System.out.println("Введите новое содержание: ");
                        data = Application.scanner.nextLine();
                        note.setInfoNote(data); break;
                    case "4" :
                        System.out.println("Введите новый срок выполнения: ");
                        data = Application.scanner.nextLine();
                        note.setDeadline(data); break;
                    case "5" :
                        menu.choiceMenu(); break;
                    default :
                        System.out.println("Нет такой команды. Введите номер команды показанный на экране.");
                        //Menu.yesOrNo(LIST_UPDATE); break;
                }
                System.out.println("Заметка успешно обновлена!");
                //Menu.yesOrNo(LIST_UPDATE);
            }
        }
        //Menu.yesOrNo(LIST_UPDATE);
    }

    public void infoNotes() {
        System.out.printf("Колличество заметок у вас - %d\n\n", Application.note.size());
        Application.note.forEach(note1 -> {
            if(!note1.getStatus().equals(CLOSED)) System.out.println(note1.getId()+" "+note1.getName()+" " + note1.getStatus()); });
        Application.note.forEach(note1 -> {
            if(note1.getStatus().equals(CLOSED)) System.out.println(note1.getId()+" "+note1.getName()+" " + note1.getStatus()); });
        *//*Program.note.stream()
                .sorted(Comparator.comparing())
                .forEach(note -> System.out.println(note.getId()+" "+note.getName()+" " + note.getStatus()));*//*
    }

    public void infoNote() throws InterruptedException {
        System.out.println("Вот список ваших заметок. Введите имя заметки для получения информации?");
        Application.note.forEach(note -> System.out.println(note.getName()));
        String name = Application.scanner.nextLine();
        Application.note.stream()
                .filter(note -> note.getName().equals(name))
                .findFirst()
                .ifPresentOrElse(System.out::println, ChoiceMenu::printNullSize);

        *//*for(Note note : Program.note) {
            if (note.getName().equals(name)) {
                System.out.println(note);
                Menu.yesOrNo(LIST_MENU);
                return;
            }
        }
        Menu.yesOrNo(NULL_SIZE);*//*
    }*/
}

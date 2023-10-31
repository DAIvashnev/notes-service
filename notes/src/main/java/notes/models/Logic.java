package notes.models;

import java.util.List;
import java.util.Scanner;

import static notes.models.Status.*;

public class Logic {
    private static Scanner scanner = new Scanner(System.in);

    public static void createNote(List<Notes> notes) throws InterruptedException {
        System.out.print("Имя вашей заметки: ");
        String name = scanner.nextLine();
        System.out.print("Содержание: ");
        String infoNote = scanner.nextLine();
        System.out.print("Срок выполнения: ");
        String deadline = scanner.nextLine();
        notes.add(new Notes(name,infoNote,deadline,NEW));
        System.out.printf("\nЗаметка успешно создана! id заметки - %s", Program.notes.get(Program.notes.size()-1).getId());
        Thread.sleep(2000);
        Menu.choiceMenu();
    }

    public static void deleteNote() throws InterruptedException {
        System.out.println("Вот список ваших заметок. Введите имя заметки для удаления?");
        Program.notes.forEach(notes -> System.out.println(notes.getName()));
        String name = scanner.nextLine();
        for(Notes notes : Program.notes) {
            if(notes.getName().equals(name)) {
                Program.notes.remove(notes);
                System.out.println("Заметка успешно удалена.");
                Thread.sleep(2000);
                Menu.choiceMenu();
                return;
            }
        }
        System.out.println("Заметка с таким именем не существует.");
        Menu.yesOrNo(LIST_MENU);
    }

    public static void updateNote() throws InterruptedException {
        String data;
        System.out.println("Вот список ваших заметок. Введите имя заметки которую хотите изменить?");
        Program.notes.forEach(notes -> System.out.println(notes.getName()));
        String name = scanner.nextLine();
        for(Notes notes : Program.notes) {
            if (notes.getName().equals(name)) {
                System.out.println("Выберите, что хотите изменить:\n1 - Имя\n2 - Заметка выполнена\n3 - Содержание\n4 - Срок выполнния\n5 - Отменить");
                data = scanner.nextLine();
                switch (data) {
                    case "1" :
                        System.out.println("Введите новое имя: ");
                        data = scanner.nextLine();
                        notes.setName(data); break;
                    case "2" :
                        notes.setStatus(CLOSED); break;
                    case "3" :
                        System.out.println("Введите новое содержание: ");
                        data = scanner.nextLine();
                        notes.setInfoNote(data); break;
                    case "4" :
                        System.out.println("Введите новый срок выполнения: ");
                        data = scanner.nextLine();
                        notes.setDeadline(data); break;
                    case "5" :
                        Menu.choiceMenu(); break;
                    default :
                        System.out.println("Нет такой команды. Введите номер команды показанный на экране.");
                        Menu.yesOrNo(LIST_UPDATE); break;
                }
                System.out.println("Заметка успешно обновлена!");
                Menu.yesOrNo(LIST_UPDATE);
            }
        }
        Menu.yesOrNo(LIST_UPDATE);
    }

    public static void infoNotes() {
        System.out.printf("Колличество заметок у вас - %d\n\n", Program.notes.size());
        Program.notes.forEach(notes1 -> {
            if(!notes1.getStatus().equals(CLOSED)) System.out.println(notes1.getId()+" "+notes1.getName()+" " + notes1.getStatus()); });
        Program.notes.forEach(notes1 -> {
            if(notes1.getStatus().equals(CLOSED)) System.out.println(notes1.getId()+" "+notes1.getName()+" " + notes1.getStatus()); });
    }

    public static void infoNote() throws InterruptedException {
        System.out.println("Вот список ваших заметок. Введите имя заметки для получения информации?");
        Program.notes.forEach(notes -> System.out.println(notes.getName()));
        String name = scanner.nextLine();
        for(Notes notes : Program.notes) {
            if (notes.getName().equals(name)) {
                System.out.println(notes);
                Menu.yesOrNo(LIST_MENU);
                return;
            }
        }
        Menu.yesOrNo(NULL_SIZE);
    }
}

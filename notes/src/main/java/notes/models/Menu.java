package notes.models;

import java.util.*;

import static notes.models.Status.MISS;
import static notes.models.Status.NULL_SIZE;

public class Menu {
    private static final List<String> MENU = new ArrayList<>(Arrays.asList("1 - Создать новую заметку.","2 - Удалить заметку.",
            "3 - Обновить заметку.","4 - Ваши заметки.","5 - Информация о заметке.","\n0 - Exit\n"));
    private static Menu menu;
    private Menu(){}
    public static Menu getMenu() {
        if(menu == null) {
            menu = new Menu();
            infoMenu();
        }
        return menu;
    }

    public static void createNote(List<Notes> notes) throws InterruptedException {
        System.out.print("Имя вашей заметки: ");
        String name = Program.scanner.nextLine();
        System.out.print("Содержание: ");
        String infoNote = Program.scanner.nextLine();
        System.out.print("Срок выполнения: ");
        String deadline = Program.scanner.nextLine();
        notes.add(new Notes(name,infoNote,deadline,Status.NEW));
        System.out.println("\nЗаметка успешно создана!");
        Thread.sleep(2000);
        Menu.infoMenu();
    }

    public static void deleteNote() throws InterruptedException {
        System.out.println("Вот список ваших заметок. Введите имя заметки для удаления?");
        Program.notes.forEach(notes -> System.out.println(notes.getName()));
        String name = Program.scanner.nextLine();
        for(Notes notes : Program.notes) {
            if(notes.getName().equals(name)) {
                Program.notes.remove(notes);
                System.out.println("Заметка успешно удалена");
                Thread.sleep(2000);
                Menu.infoMenu();
                return;
            }
        }
        System.out.println("Заметка с таким именем не существует");
        Program.yesOrNo(MISS);
    }

    public static void infoNote() {
        System.out.println("Вот список ваших заметок. Введите имя заметки для получения информации?");
        Program.notes.forEach(notes -> System.out.println(notes.getName()));
        String name = Program.scanner.nextLine();
        for(Notes notes : Program.notes) {
            if (notes.getName().equals(name)) {
                System.out.println(notes);
                Program.yesOrNo(MISS);
                return;
            }
        }
        Program.yesOrNo(NULL_SIZE);
    }

    public static void infoMenu() {
        System.out.println("\nСделайте выбор:\n");
        MENU.forEach(e -> System.out.println(e));
    }


}
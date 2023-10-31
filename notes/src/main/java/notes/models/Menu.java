package notes.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static notes.models.Status.*;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private static final List<String> MENU = new ArrayList<>(Arrays.asList("1 - Создать новую заметку.","2 - Удалить заметку.",
            "3 - Обновить заметку.","4 - Ваши заметки.","5 - Информация о заметке.","\n0 - Exit\n"));
    private static Menu menu;
    private Menu(){}
    public static Menu getMenu() throws InterruptedException {
        if(menu == null) {
            menu = new Menu();
            choiceMenu();
        }
        return menu;
    }

    public static void choiceMenu() throws InterruptedException {
        System.out.println("\nСделайте выбор:\n");
        MENU.forEach(e -> System.out.println(e));
        while (true) {
            switch (scanner.nextLine()) {
                case "1":
                    Logic.createNote(Program.notes);
                    break;
                case "2":
                    if (!Program.notes.isEmpty()) Logic.deleteNote();
                    else yesOrNo(NULL_SIZE);
                    break;
                case "3":
                    if (!Program.notes.isEmpty()) Logic.updateNote();
                    else yesOrNo(NULL_SIZE);
                    break;
                case "4":
                    Logic.infoNotes();
                    yesOrNo(LIST_MENU);
                    break;
                case "5":
                    if (!Program.notes.isEmpty()) Logic.infoNote();
                    else yesOrNo(NULL_SIZE);
                    break;
                case "0":
                    exit();
                    break;
                default:
                    System.out.println("Нет такой команды. Введите номер команды показанный на экране.");
                    yesOrNo(LIST_MENU);
                    break;
            }
        }
    }

    public static void yesOrNo(Status info) throws InterruptedException {
        String choice;
        if(info.equals(NULL_SIZE)) Status.printNullSize();
        System.out.println("Хотите продолжить? Д или Н?");
        while(true) {
            choice = scanner.nextLine();
            if("Нн".contains(choice)) {
                if(info.equals(LIST_UPDATE) || info.equals(NULL_SIZE)) choiceMenu();
                exit();
            } else if ("Дд".contains(choice)) {
                if(info.equals(LIST_UPDATE)) Logic.updateNote();
                choiceMenu();
                return;
            }
            System.out.println("Не верно. Хотите продолжть? Д или Н?");
        }
    }

    private static void exit() {
        System.out.println("\nДо свидания!");
        scanner.close();
        System.exit(0);
    }
}
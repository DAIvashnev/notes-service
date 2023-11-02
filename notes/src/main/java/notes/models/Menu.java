package notes.models;

import java.util.List;

public class Menu {
    /*static Menu menu = new Menu();
    static Logic logic = new Logic();
    private static final List<String> MENU = List.of("1 - Создать новую заметку.", "2 - Удалить заметку.",
            "3 - Обновить заметку.", "4 - Ваши заметки.", "5 - Информация о заметке.", "\n0 - Exit\n");

    public void choiceMenu() throws InterruptedException {
        System.out.println("\nСделайте выбор:\n");
        MENU.forEach(e -> System.out.println(e));
        while (true) {
            switch (Application.scanner.nextLine()) {
                case "1":
                    logic.createNote(Application.note); break;
                case "2":
                    if (!Application.note.isEmpty()) {
                        logic.deleteNote();
                    } else {
                        yesOrNo(NULL_SIZE);
                    }
                    break;
                case "3":
                    if (!Application.note.isEmpty()) {
                        logic.updateNote();
                    } else {
                        yesOrNo(NULL_SIZE);
                    }
                    break;
                case "4":
                    logic.infoNotes();
                    yesOrNo(LIST_MENU); break;
                case "5":
                    if (!Application.note.isEmpty()) {
                        logic.infoNote();
                    } else {
                        yesOrNo(NULL_SIZE);
                    }
                    break;
                case "0":
                    exit(); break;
                default:
                    System.out.println("Нет такой команды. Введите номер команды показанный на экране.");
                    //yesOrNo(LIST_MENU); break;
            }
            menu.choiceMenu();
        }
    }

    public static void yesOrNo(ChoiceMenu info) throws InterruptedException {
        String choice;
        if(info.equals(NULL_SIZE)) ChoiceMenu.printNullSize();
        System.out.println("Хотите продолжить? Д или Н?");
        while(true) {
            choice = Program.scanner.nextLine();
            if("Нн".contains(choice)) {
                if(info.equals(LIST_UPDATE) || info.equals(NULL_SIZE)) {
                    menu.choiceMenu();
                }
                exit();
            } else if ("Дд".contains(choice)) {
                if(info.equals(LIST_UPDATE)) {
                    logic.updateNote();
                }
                menu.choiceMenu();
                return;
            }
            System.out.println("Не верно. Хотите продолжть? Д или Н?");
        }
    }

    private static void exit() {
        System.out.println("\nДо свидания!");
        Application.scanner.close();
        System.exit(0);
    }*/
}
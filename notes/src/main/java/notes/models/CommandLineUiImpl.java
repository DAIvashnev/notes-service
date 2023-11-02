package notes.models;

import java.util.List;
import java.util.Scanner;

public class CommandLineUiImpl implements UserInterface{
    private NotesService notesService;
    private final Scanner scanner = new Scanner(System.in);
    private static final List<String> MENU = List.of("1 - Создать новую заметку.", "2 - Удалить заметку.",
            "3 - Обновить заметку.", "4 - Ваши заметки.", "5 - Информация о заметке.", "\n0 - Exit\n");
    InMemoryNotesServiceImpl inMNS = new InMemoryNotesServiceImpl();

    public void start() {
        while (true) {
            System.out.println("Сделайте выбор:\n");
            MENU.forEach(System.out::println);
            switch (scanner.nextLine()) {
                case "1":
                    addNote();
                    break;
                case "2":
                    menuDell();
                    break;
                case "3":
                    break;
                case "4":
                    System.out.printf("Колличество заметок у вас - %d\n\n", inMNS.getAllNotes().size());
                    inMNS.getAllNotes().forEach(System.out::println);
                    //добавить сортировку
                    break;
                case "5":
                    infoByNote();
                    break;
                case "0":
                    exit();
                    break;
                default:
                    System.out.print("Нет такой команды. Введите номер команды показанный на экране.");
                    start();
                    break;
            }
            //start();
        }
    }

    private void addNote() {
        System.out.print("Имя вашей заметки: ");
        String name = scanner.nextLine();
        System.out.print("Содержание: ");
        String infoNote = scanner.nextLine();
        System.out.print("Срок выполнения: ");
        String deadline = scanner.nextLine();
        System.out.printf("Заметка успешно создана! id заметки - %s\n\n",
                inMNS.createNote(name,infoNote,deadline).getId());
    }

    private void menuDell() {
        System.out.println("По какому критерию вести поиск удаления?:\n1 - Удалить по id.\n2 - Удалить по имени.\n3 - Отменить");
        switch (scanner.nextLine()) {
            case "1" :
                System.out.print("Введите id: ");
                if(scanner.hasNextInt()) {
                    Integer data = scanner.nextInt();
                    if(inMNS.deleteNoteById(data)) {
                        System.out.println("Заметка успешно удалена.\n");
                    } else {
                        System.out.println("Заметки c такми ID не существует.\n");
                        menuDell();
                    }
                } else {
                    System.out.println("Не корректные данные\n");
                    menuDell();
                }
                break;
            case "2" :
                System.out.print("Введите имя: ");
                if(inMNS.deleteNoteByName(scanner.nextLine())) {
                    System.out.println("Заметка успешно удалена.\n");
                } else  {
                    System.out.println("Заметки с таким именем не существует.\n");
                    menuDell();
                }
                break;
            case "3" :
                start();
                break;
            default  :
                System.out.println("Нет такой команды. Введите номер команды показанный на экране.");
                menuDell();
                break;
        }
    }

    private boolean infoByNote() {
        System.out.println("По какому критерию вести поиск заметки?:\n1 - Найти по id.\n2 - Найти по имени.\n3 - Отменить");
        switch (scanner.nextLine()) {
            case "1" :
                System.out.print("Введите id: ");
                if(!scanner.hasNextInt()) {
                    System.out.println("Не верный  id.");
                    infoByNote();
                } //else
                break;
            case "2" :
                System.out.print("Введите имя: ");
                break;
            case "3" :
                start(); break;
            default  :
                System.out.println("Нет такой команды. Введите номер команды показанный на экране.");
                infoByNote(); break;
        }
        return false;
    }

    private void exit() {
        scanner.close();
        System.exit(0);
    }

    /*public static void yesOrNo(ChoiceMenu info) throws InterruptedException {
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
    }*/
}

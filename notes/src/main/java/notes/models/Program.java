package notes.models;

import java.util.*;

import static notes.models.Status.NULL_SIZE;

public class Program {
    public static List<Notes> notes = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws InterruptedException {
        Menu.getMenu();
        while(true) {
            switch (scanner.nextLine()) {
                case "1" : Menu.createNote(notes); break;
                case "2" :
                    if(!notes.isEmpty()) Menu.deleteNote();
                    else yesOrNo(Status.NULL_SIZE);
                    break;
                case "4" :
                    System.out.printf("Колличество заметок у вас - %d\n\n", notes.size());
                    notes.forEach(notes1 -> System.out.println(notes1));
                    yesOrNo(Status.MISS); break;
                case "5" :
                    if(!notes.isEmpty()) Menu.infoNote();
                    else yesOrNo(Status.NULL_SIZE);
                    break;
                case "0" : exit(); break;
                default:
                    System.out.println("Нет такой команды. Введите номер команды показанный на экране.");
                    yesOrNo(Status.MISS); break;
            }
        }
    }

    public static void yesOrNo(Status info) {
        String choice;
        if(info.equals(NULL_SIZE)) Status.printNullSize();
        System.out.println("Хотите продолжить? Д or Н?");
        while(true) {
            choice = Program.scanner.nextLine();
            if("Нн".contains(choice)) Program.exit();
            else if ("Дд".contains(choice)) {
                Menu.infoMenu();
                return;
            }
            System.out.println("Не верно. Хотите продолжть? Д or Н?");
        }
    }

    public static void exit() {
        System.out.println("\nДо свидания!");
        scanner.close();
        System.exit(0);
    }
}

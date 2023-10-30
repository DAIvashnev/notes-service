package notes.models;

import java.util.*;

public class Program {
    public static List<Notes> notes = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Menu.getMenu();
        while(true) {
            switch (scanner.nextInt()) {
                case 1 : Menu.createNote(notes); break;
                case 5 :
                    if(!notes.isEmpty()) {
                        System.out.println("Write name note");
                        Menu.infoNote(scanner.next());
                    } else Menu.infoSizeNotes();
                case 6 :
                    System.out.printf("Your nave - %d notes\n", notes.size());
                    if(Menu.yesOrNo()) exit();
                    else Menu.infoMenu(); break;
                case 0 : exit(); break;
                default:
                    System.out.println("There is no such command! Enter another command.");
                    Menu.infoMenu(); break;
            }
        }
    }

    public static void exit() {
        System.out.println("\nGood bye!");
        scanner.close();
        System.exit(0);
    }
}

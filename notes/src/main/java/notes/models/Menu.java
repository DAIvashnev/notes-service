package notes.models;

import java.util.*;

public class Menu {
    /*private static final Map<Integer, String> MENU = new LinkedHashMap<>(Map.of(1,"- Create new note.", 2, "- Delete note.",
            3, "- Update notes.", 4, "- All notes.", 5, "- Info about note.", 6, "- Size my notes.", 0, "- Exit\n"));*/

    private static final List<String> MENU = new ArrayList<>(Arrays.asList("1 - Create new note.","2 - Delete note.",
            "3 - Update notes.","4 - All notes.","5  - Info about note.","6 - Size my notes.","\n0 - Exit\n"));
    private static Menu menu;

    private Menu(){}
    public static Menu getMenu() {
        if(menu == null) {
            menu = new Menu();
            infoMenu();
        }
        return menu;
    }

    public static List<Notes> createNote(List<Notes> notes) {
        System.out.print("Write name your note: ");
        String name = Program.scanner.next();
        System.out.print("Write info your note: ");
        String infoNote = Program.scanner.next();
        System.out.print("Write deadline your note: ");
        String deadline = Program.scanner.next();
        notes.add(new Notes(name,infoNote,deadline));
        return notes;
    }

    public static void infoNote(String nameNote) {
        //Optional<String> name;
        System.out.println(Program.notes);
        Program.notes.forEach(notes -> {
            if (notes.getName().equals(nameNote)) {
                System.out.println(notes);
            }
        });
    }

    public static void infoMenu() {
        System.out.println("\nMake your choice:\n");
        MENU.forEach(e -> System.out.println(e));
    }

    public static void infoSizeNotes() {
        System.out.println("Your don't have notes. Do you want to continue? Y or N?");
        if(Program.scanner.nextLine().equals("Y")) Menu.infoMenu();
        else Program.scanner.close(); Program.exit();
    }

    public static boolean yesOrNo() {
        System.out.println("Do you want to continue? Y or N?");
        String choice;
        while(true) {
            choice = Program.scanner.next();
            if(choice.equals("y") || choice.equals("n")) {
                return choice.equals("n");
            }
            System.out.println("Not true. Do you want to continue? Y or N?");
        }
    }
}
package notes.models;

import java.util.ArrayList;
import java.util.List;

public class Program {
    public static List<Notes> notes = new ArrayList<>();
    public static void main(String[] args) throws InterruptedException {
        Menu.getMenu();
    }
}

package notes.models;

public enum Status {
    NULL_SIZE, MISS, NEW, CLOSED;

    public static void printNullSize(){
        System.out.println("У вас нет заметок.");
    }
}

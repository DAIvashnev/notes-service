package notes.models;

public enum Status {
    NULL_SIZE, LIST_MENU, LIST_UPDATE, NEW, CLOSED;

    public static void printNullSize(){
        System.out.println("Заметка с таким именем не существует.");
    }
}

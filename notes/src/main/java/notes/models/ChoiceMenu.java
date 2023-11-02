package notes.models;

public enum ChoiceMenu {
    LIST_MENU, LIST_UPDATE, NULL_SIZE;

    public static void printNullSize(){
        System.out.println("Заметка с таким именем не существует.");
    }
}

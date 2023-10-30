package notes.models;

public class Notes {
    private String name;
    private String infoNote;
    private String deadline;
    private Status status;

    public Notes(String name, String infoNote, String deadline, Status status) {
        this.name = name;
        this.infoNote = infoNote;
        this.deadline = deadline;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Заметка - "+name+" (ожидает выполнения)"+"\nОписание: "+infoNote+"\nСрок выполнения до - "+deadline+"\n";
    }
}

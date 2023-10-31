package notes.models;


public class Notes {
    private final int id;
    private String name;
    private String infoNote;
    private String deadline;
    private Status status;

    public Notes(String name, String infoNote, String deadline, Status status) {
        this.id = NotesIdsGenerator.getInstance().generateId();
        this.name = name;
        this.infoNote = infoNote;
        this.deadline = deadline;
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInfoNote(String infoNote) {
        this.infoNote = infoNote;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInfoNote() {
        return infoNote;
    }

    public String getDeadline() {
        return deadline;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Заметка - "+name+" (id - "+id+", "+"статус - "+status+")"+"\nОписание: "+infoNote+"\nСрок выполнения до - "+deadline+"\n";
    }
}

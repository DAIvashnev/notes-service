package notes.models;

public class Notes {
    private String name;
    private String infoNote;
    private String deadline;

    public Notes(String name, String infoNote, String deadline) {
        this.name = name;
        this.infoNote = infoNote;
        this.deadline = deadline;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "name='" + name + '\'' +
                ", infoNote='" + infoNote + '\'' +
                ", deadline='" + deadline + '\'' +
                '}';
    }
}

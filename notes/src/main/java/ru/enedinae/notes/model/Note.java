package ru.enedinae.notes.model;

import ru.enedinae.notes.enumeration.NoteStatus;
import java.util.Objects;

public class Note {
    private Integer id;
    private String name;
    private String description;
    private String deadline;
    private NoteStatus status;

    public Note() {}

    public Note(String name, String description, String deadline) {
        this.name = name.length()>50 ? name.substring(0, 50) : name;
        this.description = description.length()>500 ? description.substring(0, 500) : description;
        this.deadline = deadline.length()>50 ? deadline.substring(0, 50) : deadline;
        this.status = NoteStatus.NEW;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDeadline() {
        return deadline;
    }

    public NoteStatus getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setStatus(NoteStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder noteInfo = new StringBuilder();
        if(Objects.nonNull(name) && !name.isBlank()) {
            noteInfo.append(name).append(" - ").append(status).append(" (id: ").append(id).append(")\n");
        } else {
            noteInfo.append("'not name' - ").append(name);
        }
        if(Objects.nonNull(description) && !description.isBlank()){
            noteInfo.append("Описание: ").append(description).append("\n");
        }
        if(Objects.nonNull(deadline) && !deadline.isBlank()) {
            noteInfo.append("Срок выполнения до - ").append(deadline).append("\n");
        }
        return noteInfo.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id) && Objects.equals(name, note.name) && Objects.equals(description, note.description) && Objects.equals(deadline, note.deadline) && status == note.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, deadline, status);
    }
}

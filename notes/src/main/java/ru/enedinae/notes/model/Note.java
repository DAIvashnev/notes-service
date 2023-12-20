package ru.enedinae.notes.model;

import jakarta.persistence.*;
import org.hibernate.boot.model.relational.ColumnOrderingStrategy;
import ru.enedinae.notes.enumeration.NoteStatus;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "deadline")
    private String deadline;
    @Column(name = "status")
    private NoteStatus status;
    @Column(name = "create_time")
    private String create_time;
    @Column(name = "update_time")
    private String update_time;

    public Note() {}

    public Note(String name, String description, String deadline) {
        this.name = name.length()>50 ? name.substring(0, 50) : name;
        this.description = description.length()>500 ? description.substring(0, 500) : description;
        this.deadline = deadline.length()>50 ? deadline.substring(0, 50) : deadline;
        this.status = NoteStatus.NEW;
        this.create_time = LocalDateTime.now().toString().replace("T", " ").substring(0, 16);
        this.update_time = LocalDateTime.now().toString().replace("T", " ").substring(0, 16);
    }

    public Long getId() {
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

    public String getCreate_time() { return create_time; }

    public String getUpdate_time() { return update_time; }

    public void setId(Long id) {
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

    public void setCreate_time(String create_time) { this.create_time = create_time; }

    public void setUpdate_time(String update_time) { this.update_time = update_time; }

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
        return Objects.equals(id, note.id) && Objects.equals(name, note.name) && Objects.equals(description, note.description) && Objects.equals(deadline, note.deadline) && status == note.status && Objects.equals(create_time, note.create_time) && Objects.equals(update_time, note.update_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, deadline, status, create_time, update_time);
    }
}

package ru.enedinae.notes.model;

import jakarta.persistence.*;
import org.hibernate.boot.model.relational.ColumnOrderingStrategy;
import org.springframework.core.annotation.Order;
import ru.enedinae.notes.enumeration.NoteStatus;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "deadline")
    private String deadline;
    @Column(name = "status")
    private String status;
    @Column(name = "time_create")
    private LocalDateTime time_create;
    @Column(name = "time_update")
    private LocalDateTime time_update;

    public Note() {}

    public Note(String name, String description, String deadline) {
        this.name = name.length()>50 ? name.substring(0, 50) : name;
        this.description = description.length()>500 ? description.substring(0, 500) : description;
        this.deadline = deadline.length()>50 ? deadline.substring(0, 50) : deadline;
        this.status = NoteStatus.NEW.name();
        this.time_create = LocalDateTime.now();
        this.time_update = LocalDateTime.now();
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

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreate_time() { return time_create; }

    public LocalDateTime getUpdate_time() { return time_update; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(String deadline) { this.deadline = deadline; }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreate_time(LocalDateTime create_time) { this.time_create = create_time; }

    public void setUpdate_time(LocalDateTime update_time) { this.time_update = update_time; }

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
        return Objects.equals(id, note.id) && Objects.equals(name, note.name) && Objects.equals(description, note.description) && Objects.equals(deadline, note.deadline) && Objects.equals(status, note.status) && Objects.equals(time_create, note.time_create) && Objects.equals(time_update, note.time_update);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, deadline, status, time_create, time_update);
    }
}

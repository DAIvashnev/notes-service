package ru.enedinae.notes.ui.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.enedinae.notes.model.Note;
import ru.enedinae.notes.service.NotesService;
import ru.enedinae.notes.ui.UserInterface;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.enedinae.notes.enumeration.NoteStatus.*;

@Component
public class CommandLineUiImpl implements UserInterface, CommandLineRunner {
    private final NotesService notesService;
    private final Scanner scanner = new Scanner(System.in);
    private static final Comparator<Note> NOTE_BY_STATUS_COMPARATOR = (n1, n2) -> {
        if (Objects.equals(n1.getName(), "CLOSED") && Objects.equals(n2.getName(), "NEW")) {
            return 1;
        } else if (Objects.equals(n1.getName(), "NEW") && Objects.equals(n2.getName(), "CLOSED")) {
            return -1;
        } else {
            return 0;
        }
    };

    @Autowired
    public CommandLineUiImpl(NotesService notesService) {
        this.notesService = notesService;
    }

    public void run(String... args) {
        start();
    }

    public void start() {

        while (true) {
            System.out.println("\nСделайте выбор:\n\n" + "1 - Создать новую заметку.\n" + "2 - Удалить заметку.\n" +
                    "3 - Обновить заметку.\n" + "4 - Ваши заметки.\n" + "5 - Информация о заметке.\n" + "\n0 - Exit");
            switch (scanner.nextLine()) {
                case "1":
                    addNote();
                    break;
                case "2":
                    menuDell();
                    break;
                case "3":
                    updateNote();
                    break;
                case "4":
                    allNotes();
                    break;
                case "5":
                    infoByNote();
                    break;
                case "0":
                    exit();
                    break;
                default:

                    System.out.print("Нет такой команды. Введите номер команды показанный на экране.");
                    break;
            }
        }
    }

    private void addNote() {

        System.out.print("Имя вашей заметки: ");
        String name = scanner.nextLine();
        System.out.print("Содержание: ");
        String description = scanner.nextLine();
        String deadline = validationDateTime();
        if (deadline != null) {

            System.out.printf("Заметка успешно создана! id заметки - %s\n",
                    notesService.createNote(name, description, deadline).getId());
        } else {

            System.out.println("Не корректный формат даты.");
        }
    }

    private void menuDell() {

        if (!notesService.getAllNotes().isEmpty()) {
            while (true) {
                allNotes();
                System.out.println("\n1 - Ввести ID заметки для удаления.\n2 - Отменить");
                switch (scanner.nextLine()) {
                    case "1":
                        System.out.print("Введите id: ");
                        try {
                            Long nameId = Long.parseLong(scanner.nextLine());
                            notesService.deleteNoteById(nameId);
                            Note note = notesService.getNoteById(nameId).get();
                            note.setStatus(DELETED.name());
                            notesService.updateNote(note);

                            System.out.println("Заметка успешно удалена.");
                            return;
                        } catch (Exception e) {
                            System.out.println("Не корректное ID.\n");
                        }
                        break;
                    case "2":

                        return;
                    default:

                        System.out.println("Нет такой команды. Введите номер команды показанный на экране.");
                        break;
                }
            }
        } else {
            System.out.println("У вас нет заметок.");
        }
    }

    private void updateNote() {

        allNotes();
        if (!notesService.getAllNotes().isEmpty()) {
            while (true) {
                System.out.println("\nВот список ваших заметок. Введите ID заметки которую хотите изменить?\n0 - Отменить.");
                try {
                    String name = scanner.nextLine();
                    if (name.equals("0")) {

                        return;
                    }
                    Optional<Note> newNote = notesService.getNoteById(Long.parseLong(name));
                    if (newNote.isPresent()) {
                        Note note = newNote.get();

                        System.out.println("Выберите, что хотите изменить:\n1 - Имя\n2 - Заметка выполнена\n3 - Содержание\n4 - Срок выполнния\n5 - Отменить");
                        switch (scanner.nextLine()) {
                            case "1":
                                System.out.println("Введите новое имя: ");
                                note.setName(scanner.nextLine());
                                break;
                            case "2":
                                note.setStatus(CLOSED.name());
                                break;
                            case "3":
                                System.out.println("Введите новое содержание: ");
                                note.setDescription(scanner.nextLine());
                                break;
                            case "4":
                                String deadline = validationDateTime();
                                if (deadline != null) {
                                    note.setDeadline(deadline);
                                } else {

                                    System.out.println("Не корректный формат даты.");
                                    return;
                                }
                                break;
                            case "5":

                                return;
                            default:

                                System.out.println("Нет такой команды.");
                                return;
                        }

                        System.out.println("Заметка успешно обновлена!");
                        notesService.updateNote(note);
                        return;
                    } else {

                        System.out.println("Заметки с таким ID нет.\n");
                    }
                } catch (Exception e) {

                    System.out.println("Не корректное ID.\n");
                }
            }
        }
    }

    private void allNotes() {
        if (!notesService.getAllNotes().isEmpty()) {
            AtomicInteger count = new AtomicInteger(0);
            System.out.println("Вот список ваших заметок:");
            notesService.getAllNotes().stream().sorted(NOTE_BY_STATUS_COMPARATOR).forEach(e -> {
                count.getAndIncrement();
                System.out.print(count + "." + e.getName() + " - " + e.getStatus() + " (id: " + e.getId() + ")\n");
            });
        } else {

            System.out.println("У вас нет заметок.");
        }
    }

    private void infoByNote() {

        allNotes();
        if (!notesService.getAllNotes().isEmpty()) {
            while (true) {
                System.out.println("\nПо какому критерию вести поиск заметки?:\n1 - Найти по id.\n2 - Найти по имени.\n3 - Отменить");
                switch (scanner.nextLine()) {
                    case "1":
                        System.out.print("Введите id: ");
                        try {
                            Long nameId = Long.parseLong(scanner.nextLine());
                            notesService.getNoteById(nameId).ifPresentOrElse(
                                    System.out::println,
                                    () -> System.out.println("Заметки c таким ID не существует.")
                            );
                        } catch (Exception e) {
                            System.out.println("Не корректное ID.");
                        }
                        break;
                    case "2":
                        System.out.print("Введите имя: ");
                        String name = scanner.nextLine();
                        List<Note> noteByName = notesService.getNoteByName(name);
                        if (!noteByName.isEmpty()) {
                            noteByName.forEach(System.out::println);
                        } else {
                            System.out.println("Заметки c таким именем не существует.");
                        }
                        break;
                    case "3":

                        return;
                    default:

                        System.out.println("Нет такой команды. Введите номер команды показанный на экране.");
                        break;
                }
            }
        }
    }

    private String validationDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withResolverStyle(ResolverStyle.SMART);
        System.out.print("\nСрок выполнения заметки.\nЕсли хотите продолжить без заполнения даты, нажмите 'Enter'.\nВведиде дату в формате 'гггг-мм-дд чч:мм': ");
        String dataInput = scanner.nextLine();
        if (dataInput.isEmpty()) {
            return "";
        }
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dataInput, formatter);
            return dateTime.toString().replace("T", " ");
        } catch (DateTimeException e) {
            return null;
        }
    }

    private void clearWindow() {
        System.out.print("\033[H\033[J");
    }

    private void exit() {
        scanner.close();
        System.exit(0);
    }
}

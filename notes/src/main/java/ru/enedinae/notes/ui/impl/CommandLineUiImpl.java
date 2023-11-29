package ru.enedinae.notes.ui.impl;

import ru.enedinae.notes.model.Note;
import ru.enedinae.notes.service.NotesService;
import ru.enedinae.notes.ui.UserInterface;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static ru.enedinae.notes.enumeration.NoteStatus.*;

public class CommandLineUiImpl implements UserInterface {
    private final NotesService notesService;
    private final Scanner scanner = new Scanner(System.in);
    private static final Comparator<Note> NOTE_BY_STATUS_COMPARATOR = (n1, n2) -> {
      if(n1.getStatus() == CLOSED && n2.getStatus() == NEW) {
            return 1;
      } else if (n1.getStatus() == NEW && n2.getStatus() == CLOSED) {
          return -1;
      } else {
          return 0;
      }
    };

    public CommandLineUiImpl(NotesService notesService) {
        this.notesService = notesService;
    }

    public void start() {
        while (true) {
            checkDeadline();
            System.out.println("\nСделайте выбор:\n\n"+"1 - Создать новую заметку.\n"+"2 - Удалить заметку.\n"+
                    "3 - Обновить заметку.\n"+"4 - Ваши заметки.\n"+"5 - Информация о заметке.\n"+"6 - Обновить интерфейс.\n"+"\n0 - Exit");
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
                case "6" :
                    System.out.print("\033[H\033[J");
                    break;
                case "0":
                    exit();
                    break;
                default:
                    System.out.print("Нет такой команды. Введите номер команды показанный на экране."); break;
            }
        }
    }

    private void addNote() {
        System.out.print("Имя вашей заметки: ");
        String name = scanner.nextLine();
        System.out.print("Содержание: ");
        String description = scanner.nextLine();
        System.out.print("Срок выполнения \"гггг-мм-дд\": ");
        String deadline = scanner.nextLine();
        System.out.printf("Заметка успешно создана! id заметки - %s\n",
                notesService.createNote(name,description,deadline).getId());
    }

    private void menuDell() {
        if(!notesService.getAllNotes().isEmpty()) {
            while(true) {
                allNotes();
                System.out.println("\n1 - Ввести ID заметки для удаления.\n2 - Отменить");
                switch (scanner.nextLine()) {
                    case "1":
                        System.out.print("Введите id: ");
                        try {
                            Integer nameId = Integer.parseInt(scanner.nextLine());
                            if (notesService.deleteNoteById(nameId)) {
                                Note note = notesService.getNoteById(nameId).get();
                                note.setStatus(DELETED);
                                notesService.updateNote(note);
                                System.out.println("Заметка успешно удалена.");
                                return;
                            } else {
                                System.out.println("Заметки c такми ID не существует.\n");
                            }
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
        if(!notesService.getAllNotes().isEmpty()) {
            while(true) {
                allNotes();
                System.out.println("\nВот список ваших заметок. Введите ID заметки которую хотите изменить?");
                try {
                    String name = scanner.nextLine();
                    Optional<Note> newNote = notesService.getNoteById(Integer.parseInt(name));
                    if (newNote.isPresent()) {
                        Note note = newNote.get();
                        System.out.println("Выберите, что хотите изменить:\n1 - Имя\n2 - Заметка выполнена\n3 - Содержание\n4 - Срок выполнния\n5 - Отменить");
                        switch (scanner.nextLine()) {
                            case "1":
                                System.out.println("Введите новое имя: ");
                                note.setName(scanner.nextLine());
                                break;
                            case "2":
                                note.setStatus(CLOSED);
                                break;
                            case "3":
                                System.out.println("Введите новое содержание: ");
                                note.setDescription(scanner.nextLine());
                                break;
                            case "4":
                                System.out.println("Введите новый срок выполнения: ");
                                note.setDeadline(scanner.nextLine());
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
        } else {
            System.out.println("У вас нет заметок.");
        }
    }

    private void allNotes() {
        AtomicInteger count = new AtomicInteger(0);
        System.out.printf("Колличество заметок у вас - %d\n", notesService.getAllNotes().size());
        notesService.getAllNotes().stream().sorted(NOTE_BY_STATUS_COMPARATOR).forEach(e-> {
            count.getAndIncrement();
            System.out.print(count+"."+e.getName()+" - "+e.getStatus()+" (id: "+e.getId()+")\n");
        });
    }

    private void infoByNote() {
        if(!notesService.getAllNotes().isEmpty()) {
            while(true) {
                System.out.println("\nПо какому критерию вести поиск заметки?:\n1 - Найти по id.\n2 - Найти по имени.\n3 - Отменить");
                switch (scanner.nextLine()) {
                    case "1":
                        System.out.print("Введите id: ");
                        try {
                            Integer nameId = Integer.parseInt(scanner.nextLine());
                            notesService.getNoteById(nameId).ifPresentOrElse(
                                    System.out::println,
                                    ()-> System.out.println("Заметки c таким ID не существует.")
                            );
                        } catch (Exception e) {
                            System.out.println("Не корректное ID.");
                        }
                        break;
                    case "2":
                        System.out.print("Введите имя: ");
                        String name = scanner.nextLine();
                        List<Note> noteByName = notesService.getNoteByName(name);
                        if(!noteByName.isEmpty()) {
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
        } else {
            System.out.println("У вас нет заметок.");
        }
    }

    private void checkDeadline() {
        List<Note> notes = notesService.getAllNotes();
        notes.forEach(note -> {
                try {
                    if ((LocalDate.now()).isAfter(LocalDate.parse(note.getDeadline()))) {
                        note.setStatus(DELAY);
                        notesService.updateNote(note);
                        System.out.printf("\nЗаметка - \"%s\" просрочена.\n", note.getName());
                    }
                } catch (DateTimeException e) {}
        });
    }

    private void exit() {
        scanner.close();
        System.exit(0);
    }
}

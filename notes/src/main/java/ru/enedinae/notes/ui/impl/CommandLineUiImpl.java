package ru.enedinae.notes.ui.impl;

import ru.enedinae.notes.service.NotesService;
import ru.enedinae.notes.ui.UserInterface;
import ru.enedinae.notes.model.Note;

import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.enedinae.notes.enumeration.NoteStatus.CLOSED;
import static ru.enedinae.notes.enumeration.NoteStatus.NEW;

// отладить меню - menuDell()
// переработать updateNote()


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
            System.out.println("\nСделайте выбор:\n\n"+"1 - Создать новую заметку.\n"+"2 - Удалить заметку.\n"+
                    "3 - Обновить заметку.\n"+"4 - Ваши заметки.\n"+"5 - Информация о заметке.\n"+"\n0 - Exit");
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
                    System.out.print("Нет такой команды. Введите номер команды показанный на экране."); break;
            }
        }
    }

    private void addNote() {
        System.out.print("Имя вашей заметки: ");
        String name = scanner.nextLine();
        System.out.print("Содержание: ");
        String infoNote = scanner.nextLine();
        System.out.print("Срок выполнения: ");
        String deadline = scanner.nextLine();
        System.out.printf("Заметка успешно создана! id заметки - %s\n",
                notesService.createNote(name,infoNote,deadline).getId());
    }

    private void menuDell() {
        if(!notesService.getAllNotes().isEmpty()) {
            while(true) {
                System.out.println("По какому критерию вести поиск удаления?:\n1 - Удалить по id.\n2 - Удалить по имени.\n3 - Отменить");
                switch (scanner.nextLine()) {
                    case "1":
                        System.out.print("Введите id: ");

                        // отладить вызовы

                        try {
                            Integer nameId = Integer.parseInt(scanner.nextLine());
                            if (notesService.deleteNoteById(nameId)) {
                                System.out.println("Заметка успешно удалена.");
                            } else {
                                System.out.println("Заметки c такми ID не существует.\n");
                            }
                        } catch (Exception e) {
                            System.out.println("Не корректное ID.\n");
                        }
                        break;
                    case "2":
                        System.out.print("Введите имя: ");
                        if (notesService.deleteNoteByName(scanner.nextLine())) {
                            System.out.println("Заметка успешно удалена.");
                        } else {
                            System.out.println("Заметки с таким именем не существует.\n");
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

    private void updateNote() {
        if(!notesService.getAllNotes().isEmpty()) {
            System.out.println("Вот список ваших заметок. Введите имя заметки которую хотите изменить?");
            notesService.getAllNotes().forEach(e-> System.out.println(e.getName()));
            String name = scanner.nextLine();
            Optional<Note> checkNote = notesService.getNoteByName(name);
            if (checkNote.isPresent()) {
                Note newNote = notesService.getAllNotes().stream().filter(e->e.getName().equals(name)).findFirst().get();
                System.out.println("Выберите, что хотите изменить:\n1 - Имя\n2 - Заметка выполнена\n3 - Содержание\n4 - Срок выполнния\n5 - Отменить");
                switch (scanner.nextLine()) {
                    case "1" :
                        System.out.println("Введите новое имя: ");
                        newNote.setName(scanner.nextLine());
                        break;
                    case "2" :
                        newNote.setStatus(CLOSED);
                        break;
                    case "3" :
                        System.out.println("Введите новое содержание: ");
                        newNote.setDescription(scanner.nextLine());
                        break;
                    case "4" :
                        System.out.println("Введите новый срок выполнения: ");
                        newNote.setDeadline(scanner.nextLine());
                        break;
                    case "5" :
                        return;
                    default :
                        System.out.println("Нет такой команды.");
                        return;
                }
                System.out.println("Заметка успешно обновлена!");
                notesService.updateNote(newNote);
            } else {
                System.out.println("Заметки с таким именем нет.");
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
            System.out.println(count+"."+e.getName()+" - "+e.getStatus());
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
                                    ()-> System.out.println("Заметки c такми ID не существует.\n")
                            );
                        } catch (Exception e) {
                            System.out.println("Не корректное ID.\n");
                        }
                        break;
                    case "2":
                        System.out.print("Введите имя: ");
                        String name = scanner.nextLine();
                        notesService.getNoteByName(name).ifPresentOrElse(
                                System.out::println,
                                ()-> System.out.println("Заметки с таким именем не существует.\n")
                        );
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

    private void exit() {
        scanner.close();
        System.exit(0);
    }
}

package notes.models;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static notes.models.NoteStatus.CLOSED;

// отладить меню - menuDell()
// добавит сортировку - allNotes()
// корректно распечатать Optional - infoByNote()
// не выводить пустые поля - infoByNote()
// переработать updateNote()


public class CommandLineUiImpl implements UserInterface{
    private NotesService notesService;
    private final Scanner scanner = new Scanner(System.in);
    InMemoryNotesServiceImpl inMNS = new InMemoryNotesServiceImpl();

    public void start() {
        while (true) {
            System.out.println("\nСделайте выбор:\n\n"+"1 - Создать новую заметку.\n"+"2 - Удалить заметку.\n"+
                    "3 - Обновить заметку.\n"+"4 - Ваши заметки.\n"+"5 - Информация о заметке.\n"+"\n0 - Exit");
            switch (scanner.nextLine()) {
                case "1":
                    addNote(); break;
                case "2":
                    menuDell(); break;
                case "3":
                    updateNote(); break;
                case "4":
                    allNotes();
                    break;
                case "5":
                    infoByNote(); break;
                case "0":
                    exit(); break;
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
                inMNS.createNote(name,infoNote,deadline).getId());
    }

    private void menuDell() {
        if(!inMNS.getAllNotes().isEmpty()) {
            System.out.println("По какому критерию вести поиск удаления?:\n1 - Удалить по id.\n2 - Удалить по имени.\n3 - Отменить");
            switch (scanner.nextLine()) {
                case "1" :
                    System.out.print("Введите id: ");

                    // отладить вызовы

                    try {
                        Integer nameId = Integer.parseInt(scanner.nextLine());
                        if(inMNS.deleteNoteById(nameId)) {
                            System.out.println("Заметка успешно удалена.");
                        } else {
                            System.out.println("Заметки c такми ID не существует.\n");
                            menuDell();
                        }
                    } catch (Exception e) {
                        System.out.println("Не корректное ID.\n");
                        menuDell();
                    }
                    break;
                case "2" :
                    System.out.print("Введите имя: ");
                    if(inMNS.deleteNoteByName(scanner.nextLine())) {
                        System.out.println("Заметка успешно удалена.");
                    } else  {
                        System.out.println("Заметки с таким именем не существует.\n");
                        menuDell();
                    }
                    break;
                case "3" :
                    start();
                    break;
                default  :
                    System.out.println("Нет такой команды. Введите номер команды показанный на экране.");
                    menuDell();
                    break;
            }
        } else {
            System.out.println("У вас нет заметок.");
        }
    }

    private void updateNote() {
        if(!inMNS.getAllNotes().isEmpty()) {
            System.out.println("Вот список ваших заметок. Введите имя заметки которую хотите изменить?");
            inMNS.getAllNotes().forEach(e-> System.out.println(e.getName()));
            String name = scanner.nextLine();
            Optional<Note> checkNote = inMNS.getAllNotes().stream().filter(e->e.getName().equals(name)).findFirst();
            if (checkNote.isPresent()) {
                Note newNote = inMNS.getAllNotes().stream().filter(e->e.getName().equals(name)).findFirst().get();
                System.out.println("Выберите, что хотите изменить:\n1 - Имя\n2 - Заметка выполнена\n3 - Содержание\n4 - Срок выполнния\n5 - Отменить");
                switch (scanner.nextLine()) {
                    case "1" :
                        System.out.println("Введите новое имя: ");
                        newNote.setName(scanner.nextLine());
                        inMNS.updateNote(newNote); break;
                    case "2" :
                        newNote.setStatus(CLOSED);
                        inMNS.updateNote(newNote); break;
                    case "3" :
                        System.out.println("Введите новое содержание: ");
                        newNote.setDescription(scanner.nextLine());
                        inMNS.updateNote(newNote); break;
                    case "4" :
                        System.out.println("Введите новый срок выполнения: ");
                        newNote.setDeadline(scanner.nextLine());
                        inMNS.updateNote(newNote); break;
                    case "5" :
                        start(); break;
                    default :
                        System.out.println("Нет такой команды. Введите номер команды показанный на экране.");
                }
                System.out.println("Заметка успешно обновлена!");
            } else {
                System.out.println("Заметки с таким именем нет.");
            }
        } else {
            System.out.println("У вас нет заметок.");
        }
    }

    private void allNotes() {
        AtomicInteger count = new AtomicInteger(0);
        System.out.printf("Колличество заметок у вас - %d\n", inMNS.getAllNotes().size());
        inMNS.getAllNotes().forEach(e-> {
            count.getAndIncrement();
            System.out.println(count+"."+e.getName()+" - "+e.getStatus());
        });
    }

    private void infoByNote() {
        if(!inMNS.getAllNotes().isEmpty()) {
            System.out.println("По какому критерию вести поиск заметки?:\n1 - Найти по id.\n2 - Найти по имени.\n3 - Отменить");
            switch (scanner.nextLine()) {
                case "1" :
                    System.out.print("Введите id: ");
                    try {
                        Integer nameId = Integer.parseInt(scanner.nextLine());
                        if(inMNS.getNoteById(nameId).isPresent()) {

                            // распечатать Optional

                            System.out.println(inMNS.getNoteById(nameId));
                        } else {
                            System.out.println("Заметки c такми ID не существует.\n");
                            infoByNote();
                        }
                    } catch (Exception e) {
                        System.out.println("Не корректное ID.\n");
                        infoByNote();
                    }
                    break;
                case "2" :
                    System.out.print("Введите имя: ");
                    String name = scanner.nextLine();
                    if(inMNS.getNoteByName(name).isPresent()) {

                        // распечатать Optional

                        System.out.println(inMNS.getNoteByName(name));
                    } else  {
                        System.out.println("Заметки с таким именем не существует.\n");
                        infoByNote();
                    }
                    break;
                case "3" :
                    start(); break;
                default  :
                    System.out.println("Нет такой команды. Введите номер команды показанный на экране.");
                    infoByNote(); break;
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

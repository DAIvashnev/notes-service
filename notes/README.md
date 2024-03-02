# notes-service

**notes_srvice** - это сервисное приложение, написанное на языке Java 11 с применением технологий: Spring Boot, Spring Data JPA, PostgreSQL, WebMVC.

Приложение **notes** - позволит Вам вести учет своих заметок, как в онлайн, так и в офлайн режимах.

Проект имеет разные версии реализации приложния, начиная от низкоуровневого использования подключения к базe данных (JDBC) и выводом результатов на консоль, заканчивая более технологичными и современными инструментами, такими как Spring Boot, Spring Data JPA и WebMVC, которые позволяют следить за deadline заметок и взаимодействовать с ними, уже в более удобной среде, через **http://** запросы в браузере.

### Характеристики

+ **Release 1.0** - Написан на чистой Java 11, поддерживает подключение к базе данных соединением JDBC. Работа и управление просиходит через консоль. Содержит в себе два способа использования:
  
1. *Локально в памяти.*
```bash
.../notes/target java -jar notes-1.0.0-shaded.jar --service.mode=in-memory
```  
2. *В базе данных.*
```bash
.../notes/target java -jar notes-1.0.0-shaded.jar --service.mode=data-base
``` 
+ **Release 1.1** - Реализация проекта на Spring Framework. Хранит данные только в БД и запускается без аргументов.

+ **Release 1.2** - Все тоже самое что и в верии 1.1, изменена реализация хранения данных в БД, на JdbcTemplate.

+ **Release 1.3** - В это версии проект полность реализован на Spring Boot.
  
+ **Release 2.0** - Финальная версия проекта, который написан на Spring Boot. Подключение к БД осуществляется через Spring Data JPA, а взаимодействие с приложением через Spring WebMVC.
```bash
.../notes/target java -jar notes-2.0.0.jar
```
### Реализация *REST API* для совершеня http:// запроссов:
```java
@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NotesService notesService;
    @Autowired
    public NoteController(NotesService notesService, NoteRepository noteRepository) {
        this.notesService = notesService;
    }

    @GetMapping
    public ResponseEntity getAllNotes() {
        try {
            return ResponseEntity.ok(notesService.getAllNotes());
        } catch (NotHaveNotesException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("createNote")
    public ResponseEntity createNote(@RequestBody Note note) {
        try {
            notesService.createNote(note.getName(),note.getDescription(),note.getDeadline());
            return ResponseEntity.ok("Заметка успешно создана");
        } catch (NotCorrectFormatDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getNoteById")
    public ResponseEntity getNoteById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(notesService.getNoteById(id));
        } catch (NoteNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getNoteByName")
    public ResponseEntity getNoteByName(@RequestParam String name) {
        try {
            return ResponseEntity.ok(notesService.getNoteByName(name));
        } catch (NoteNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/deleteNoteById")
    public ResponseEntity deleteNoteById(@RequestParam Long id) {
        try {
            notesService.deleteNoteById(id);
            return ResponseEntity.ok("Заметка успешно удалена");
        } catch (NoteNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateNoteById")
    public ResponseEntity updateNoteById(@RequestParam Long id, @RequestBody Note note) {
        try {
            notesService.updateNote(id, note);
            return ResponseEntity.ok("Заметка успешно обновлена");
        } catch (NoteNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotCorrectFormatDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
```
### Конфигурация ***pom.xml*** файла.

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.0</version>
  </parent>
```

```xml
<parent>
    <dependencies>
    <dependency>
      <groupId>org.postgresql</groupId>
      <artifactId>postgresql</artifactId>
      <version>42.6.0</version>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
      <version>3.2.0</version>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
      <version>3.2.1</version>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>
  </parent>
```
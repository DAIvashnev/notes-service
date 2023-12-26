package ru.enedinae.notes.exceptions;

public class NoteNotFoundException extends Exception{
    public NoteNotFoundException(String message) {
        super(message);
    }
}

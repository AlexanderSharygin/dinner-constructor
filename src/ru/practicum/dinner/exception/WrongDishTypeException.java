package ru.practicum.dinner.exception;

public class WrongDishTypeException extends RuntimeException {
    public WrongDishTypeException(String message) {
        super(message);
    }
}
package ru.practicum.dinner.exception;

public class DishAlreadyExistException extends RuntimeException {
    public DishAlreadyExistException(String message) {
        super(message);
    }
}
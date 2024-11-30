package ru.practicum.dinner.model;

public enum DishType {
    FIRST("1"),
    SECOND("2"),
    DRINK("3");

    private final String value;

    DishType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

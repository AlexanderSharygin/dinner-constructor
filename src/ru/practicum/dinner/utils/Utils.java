package ru.practicum.dinner.utils;

public class Utils {
    public static int tryParseInputValue(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
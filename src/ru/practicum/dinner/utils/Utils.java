package ru.practicum.dinner.utils;

import ru.practicum.dinner.model.DishType;

public class Utils {
    public static int tryParseInputValue(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static DishType calculateDishTypeEnumValue(String dishTypeInput) {
        for (DishType dishType : DishType.values()) {
            if (dishType.getValue().equalsIgnoreCase(dishTypeInput.trim())) {
                return dishType;
            }
        }
        return null;
    }
}
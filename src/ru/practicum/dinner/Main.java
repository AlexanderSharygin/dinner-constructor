package ru.practicum.dinner;

import ru.practicum.dinner.constructor.DinnerConstructor;
import ru.practicum.dinner.exception.DishAlreadyExistException;
import ru.practicum.dinner.model.Dish;
import ru.practicum.dinner.utils.Utils;

import java.util.*;

public class Main {

    static DinnerConstructor dinnerConstructor;
    static Scanner scanner;

    public static void main(String[] args) {
        dinnerConstructor = new DinnerConstructor();
        scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            String command = scanner.next();
            int commandCode = Utils.tryParseInputValue(command);
            switch (commandCode) {
                case 1 -> addNewDish();
                case 2 -> generateDishCombo();
                case 3 -> {
                    return;
                }
                default -> System.out.println("Нет такой команды. Попробуй еще раз!");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Выберите команду:");
        System.out.println("1 - Добавить новое блюдо");
        System.out.println("2 - Сгенерировать комбинации блюд");
        System.out.println("3 - Выход");
    }

    private static void addNewDish() {
        System.out.println("Введите тип блюда:");
        String dishType = scanner.next();
        System.out.println("Введите название блюда:");
        String dishName = scanner.next();
        try {
            dinnerConstructor.addDish(dishType, dishName);
        } catch (DishAlreadyExistException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void generateDishCombo() {
        System.out.println("Начинаем конструировать обед...");
        System.out.println("Введите количество наборов, которые нужно сгенерировать:");
        String inputValue = scanner.next();
        int numberOfCombos = Utils.tryParseInputValue(inputValue);
        if (numberOfCombos < 1) {
            System.out.println("Количество должно быть положительным числом");
            return;
        }
        scanner.nextLine();
        System.out.println("Вводите типы блюда, разделяя символом переноса строки (enter). " +
                "Для завершения ввода введите пустую строку");
        List<String> types = getDishTypesListForComboCreation();
        if (!types.isEmpty()) {
            HashMap<UUID, ArrayList<Dish>> dishCombos = dinnerConstructor.getDinnerCombos(numberOfCombos, types);
            printDinerCombinations(dishCombos, numberOfCombos);
        } else {
            System.out.println("Вы не указали типы блюд");
        }

    }

    private static List<String> getDishTypesListForComboCreation() {
        List<String> types = new ArrayList<>();
        while (true) {
            String inputDishType = scanner.nextLine();
            if (inputDishType.isEmpty()) {
                break;
            }
            types.add(inputDishType);
        }
        return types;
    }

    private static void printDinerCombinations(HashMap<UUID, ArrayList<Dish>> dinnerCombos, int expectedNumberOfCombos) {
        int counter = 1;
        if (dinnerCombos.keySet().isEmpty()) {
            System.out.println("Не удалось сгенерировать комбинации блюд. " +
                    "В списке блюд отсутствует один или несколько типов блюд необходимых для генерации комбинаций");
        } else if (dinnerCombos.keySet().size() != expectedNumberOfCombos) {
            System.out.println("Не удалось сгенерировать запрошенное количество комбинаций блюд. " +
                    "В списке блюд отсутствует один или несколько типов блюд необходимых для генерации комбинаций");
            System.out.println("Всего сгенерировано " + dinnerCombos.keySet().size() +
                    " комбинаций вместо " + expectedNumberOfCombos);
        }
        for (UUID key : dinnerCombos.keySet()) {
            System.out.println("Комбо " + counter + ": ");
            System.out.println(dinnerCombos.get(key));
            counter++;
        }
    }
}
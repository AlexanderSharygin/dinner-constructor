package ru.practicum.dinner.constructor;

import ru.practicum.dinner.exception.DishAlreadyExistException;
import ru.practicum.dinner.model.Dish;

import java.util.*;

public class DinnerConstructor {

    private final Map<String, ArrayList<Dish>> dishesByTypes = new HashMap<>();

    public void addDish(String dishTypeInput, String dishNameInput) {

        Dish dish = new Dish(UUID.randomUUID(), dishNameInput);
        if (dishesByTypes.containsKey(dishTypeInput)) {
            List<String> dishesNames = dishesByTypes.get(dishTypeInput).stream().map(Dish::name).toList();
            if (!dishesNames.contains(dishNameInput)) {
                dishesByTypes.get(dishTypeInput).add(dish);
            } else {
                throw new DishAlreadyExistException("Блюдо с названием " + dishNameInput +
                        " уже добавлено в категорию " + dishTypeInput);
            }
        } else {
            ArrayList<Dish> dishesNames = new ArrayList<>();
            dishesNames.add(dish);
            dishesByTypes.put(dishTypeInput, dishesNames);
        }

    }

    public HashMap<UUID, ArrayList<Dish>> getDinnerCombos(int inputCount, List<String> types) {
        HashMap<UUID, ArrayList<Dish>> dishesCombos = new HashMap<>();
        for (String dishType : types) {
            if (dishesByTypes.get(dishType) == null) {
                return dishesCombos;
            }
        }
        while (dishesCombos.values().size() != inputCount) {
            ArrayList<Dish> newCombo = new ArrayList<>();
            for (var dishType : types) {
                int index = new Random().nextInt(dishesByTypes.get(dishType).size());
                Dish dish = dishesByTypes.get(dishType).get(index);
                newCombo.add(dish);
            }
            dishesCombos.put(UUID.randomUUID(), newCombo);
        }
        return dishesCombos;
    }
}
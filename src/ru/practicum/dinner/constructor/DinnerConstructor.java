package ru.practicum.dinner.constructor;

import ru.practicum.dinner.exception.DishAlreadyExistException;
import ru.practicum.dinner.exception.WrongDishTypeException;
import ru.practicum.dinner.model.Dish;
import ru.practicum.dinner.model.DishType;

import java.util.*;

import static ru.practicum.dinner.utils.Utils.calculateDishTypeEnumValue;

public class DinnerConstructor {

    private final Map<DishType, ArrayList<Dish>> dishesByTypes = new HashMap<>();

    public void addDish(String dishTypeInput, String dishNameInput) {

        DishType dishType = calculateDishTypeEnumValue(dishTypeInput);
        if (dishType == null) {
            throw new WrongDishTypeException("Указан неверный тип блюда");
        }
        Dish dish = new Dish(UUID.randomUUID(), dishNameInput);
        if (dishesByTypes.containsKey(dishType)) {
            List<String> dishesNames = dishesByTypes.get(dishType).stream().map(Dish::name).toList();
            if (!dishesNames.contains(dishNameInput)) {
                dishesByTypes.get(dishType).add(dish);
            } else {
                throw new DishAlreadyExistException("Блюдо с названием " + dishNameInput +
                        " уже добавлено в категорию " + dishTypeInput);
            }
        } else {
            ArrayList<Dish> dishesNames = new ArrayList<>();
            dishesNames.add(dish);
            dishesByTypes.put(dishType, dishesNames);
        }

    }

    public HashMap<UUID, ArrayList<Dish>> getDinnerCombos(int inputCount, Set<DishType> types) {
        HashMap<UUID, ArrayList<Dish>> dishesCombos = new HashMap<>();
        int dishesMaxCountInCategories = 0;
        for (DishType dishType : types) {
            if (dishesByTypes.get(dishType) == null) {
                dishesMaxCountInCategories = 0;
                break;
            } else if (dishesByTypes.get(dishType).size() > dishesMaxCountInCategories) {
                dishesMaxCountInCategories = dishesByTypes.get(dishType).size();
            }
        }
        if (dishesMaxCountInCategories == 0) {
            return dishesCombos;
        }
        int count = Math.min(dishesMaxCountInCategories, inputCount);
        while (dishesCombos.values().size() != count) {
            ArrayList<Dish> newCombo = new ArrayList<>();
            for (var dishType : types) {
                int index = new Random().nextInt(dishesByTypes.get(dishType).size());
                Dish dish = dishesByTypes.get(dishType).get(index);
                newCombo.add(dish);
            }
            boolean isTheSameCombo = isComboAlreadyExist(dishesCombos, newCombo);
            if (!isTheSameCombo) {
                dishesCombos.put(UUID.randomUUID(), newCombo);
            }
        }
        return dishesCombos;
    }

    private boolean isComboAlreadyExist(HashMap<UUID, ArrayList<Dish>> dishesCombos, List<Dish> newCombo) {
        if (dishesCombos.keySet().isEmpty()) {
            return false;
        }
        for (UUID key : dishesCombos.keySet()) {
            boolean isTheSameCombo = true;
            if (dishesCombos.get(key).size() == newCombo.size()) {
                for (int i = 0; i < newCombo.size(); i++) {
                    if (!dishesCombos.get(key).get(i).equals(newCombo.get(i))) {
                        isTheSameCombo = false;
                        break;
                    }
                }
                if (!isTheSameCombo) {
                    return false;
                }
            }
        }
        return true;
    }
}
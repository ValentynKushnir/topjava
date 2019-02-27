package ru.javawebinar.topjava.dao;

import java.util.List;

import ru.javawebinar.topjava.model.Meal;

public interface MealDao {
    Meal findMealById(int id);
    void saveMeal(Meal meal);
    void updateMeal(Meal meal);
    void removeMealById(int id);
    List<Meal> findAllMeals();
    int getCaloriesPerDay();
    int getNextId();
}

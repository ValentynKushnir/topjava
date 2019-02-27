package ru.javawebinar.topjava.service;

import java.time.LocalDateTime;
import java.util.List;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

public interface MealSrv {
    void createMeal(String description, LocalDateTime dateTIme, int calories);
    Meal getMealById(int id);
    void updateMeal(int id, String desctiption, LocalDateTime dateTime, int calories);
    void removeMealById(int id);
    public List<MealTo> getFilteredWithExcess();
}

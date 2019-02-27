package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealDto {
    private final int id;
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;

    public MealDto(int id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }
}

package ru.javawebinar.topjava.dao;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ru.javawebinar.topjava.model.Meal;

public class MealDaoInMemoryImpl implements MealDao {
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    private static final Integer CALORIES_PER_DAY = 2000;
    private static final Map<Integer, Meal> MEALS_BY_ID = new ConcurrentHashMap<>();

    static {
        populateDb();
    }

    private static void populateDb() {
        Meal meal1 = createMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
        Meal meal2 = createMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
        Meal meal3 = createMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
        Meal meal4 = createMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
        Meal meal5 = createMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
        Meal meal6 = createMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
        MEALS_BY_ID.putAll(Stream.of(meal1, meal2, meal3, meal4, meal5, meal6).collect(Collectors.toMap(Meal::getId, Function.identity())));
    }

    private static Meal createMeal(LocalDateTime dateTime, String description, int calories) {
        return new Meal(COUNTER.incrementAndGet(), dateTime, description, calories);
    }

    public int getNextId() {
        return COUNTER.incrementAndGet();
    }

    @Override
    public Meal findMealById(int id) {
        return MEALS_BY_ID.getOrDefault(id, null);
    }

    @Override
    public void saveMeal(Meal meal) {
        if (MEALS_BY_ID.containsKey(meal.getId())) {
            updateMeal(meal);
        } else {
            MEALS_BY_ID.put(meal.getId(), meal);
        }
    }

    @Override
    public void updateMeal(Meal meal) {
        MEALS_BY_ID.put(meal.getId(), meal);
    }

    @Override
    public void removeMealById(int id) {
        MEALS_BY_ID.remove(id);
    }

    @Override
    public List<Meal> findAllMeals() {
        return new ArrayList(MEALS_BY_ID.values());
    }

    public int getCaloriesPerDay() {
        return CALORIES_PER_DAY;
    }
}

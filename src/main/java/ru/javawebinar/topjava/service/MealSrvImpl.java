package ru.javawebinar.topjava.service;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.ValidationUtils;

public class MealSrvImpl implements MealSrv {

    private MealDao mealDao;

    public MealSrvImpl() {
        mealDao = new MealDaoInMemoryImpl();
    }

    @Override
    public void createMeal(String description, LocalDateTime dateTime, int calories) {
        Objects.requireNonNull(description, "description must be not null");
        Objects.requireNonNull(dateTime, "date and time must be not null");
        ValidationUtils.requireNotNullOrEmpty(calories, "calories must not be null");
        Meal newMeal = new Meal(mealDao.getNextId(), dateTime, description, calories);
        mealDao.saveMeal(newMeal);
    }

    @Override
    public Meal getMealById(int id) {
        return mealDao.findMealById(id);
    }

    @Override
    public void updateMeal(int id, String description, LocalDateTime dateTime, int calories) {
        Meal mealToUpdate = new Meal(id, dateTime, description, calories);
        mealDao.updateMeal(mealToUpdate);
    }

    @Override
    public void removeMealById(int id) {
        mealDao.removeMealById(id);
    }

    public List<MealTo> getFilteredWithExcess() {
        return getFilteredWithExcessInOnePass(
                mealDao.findAllMeals(), LocalTime.MIN, LocalTime.MAX, mealDao.getCaloriesPerDay());
    }

    public List<MealTo> getFilteredWithExcess(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExcess(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public List<MealTo> getFilteredWithExcessByCycle(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<MealTo> mealsWithExcess = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealsWithExcess.add(createWithExcess(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay));
            }
        });
        return mealsWithExcess;
    }

    public List<MealTo> getFilteredWithExcessInOnePass(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Collection<List<Meal>> list = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate)).values();

        return list.stream().flatMap(dayMeals -> {
            boolean excess = dayMeals.stream().mapToInt(Meal::getCalories).sum() > caloriesPerDay;
            return dayMeals.stream().filter(meal ->
                    TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                    .map(meal -> createWithExcess(meal, excess));
        }).collect(toList());
    }

    public MealTo createWithExcess(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
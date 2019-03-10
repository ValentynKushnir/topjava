package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.web.SecurityUtil.loggedUserId;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m -> save(loggedUserId(), m));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        Map<Integer, Meal> meals = repository.containsKey(userId) ? repository.get(userId) : new HashMap<>();
        meals.put(meal.getId(), meal);
        if (!repository.containsKey(userId)) {
            repository.put(userId, meals);
        }
        return meal;
    }

    @Override
    public boolean delete(int userId, int id) {
        Map<Integer, Meal> meals = repository.get(userId);
        if (meals == null) {
            return false;
        }
        for (Map.Entry<Integer, Meal> pair : meals.entrySet()) {
            Meal next = pair.getValue();
            if (next.getId().equals(id)) {
                meals.remove(pair.getKey());
                return true;
            }
        }
        return false;
    }

    @Override
    public Meal get(int userId, int id) {
        Map<Integer, Meal> meals = repository.get(userId);
        if (meals == null) {
            return null;
        }
        return meals.values().stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> integerMealMap = repository.get(userId);
        if (integerMealMap == null) {
            return Collections.emptyList();
        }
        List<Meal> meals = new ArrayList<>(integerMealMap.values());
        return meals.stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }
}


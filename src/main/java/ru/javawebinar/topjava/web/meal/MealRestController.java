package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.to.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.loggedUserId;

@Controller
public class MealRestController {
    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal save(Meal meal) {
        return service.save(loggedUserId(), meal);
    }

    public void delete(int id) {
        service.delete(loggedUserId(), id);
    }

    public Meal get(int id) {
        return service.get(loggedUserId(), id);
    }

    public List<MealTo> getAll() {
        return MealsUtil.getWithExcess(service.getAll(loggedUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

}
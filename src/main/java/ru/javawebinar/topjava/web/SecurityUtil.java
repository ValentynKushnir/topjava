package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotValidUserException;

import java.util.Arrays;
import java.util.List;

public class SecurityUtil {

    private static final User SAAS_ADMIN = new User(0, "Admin", "admin@mail.com", "admin", Role.ROLE_ADMIN);
    private static final User REGULAR_USER = new User(1, "User", "user@mail.com", "user", Role.ROLE_USER);
    private static final List<User> USERS = Arrays.asList(
            SAAS_ADMIN, REGULAR_USER
    );

    private static int loggedUserId;

    static {
        logIn(SAAS_ADMIN.getId());
    }

    public static List<User> getAvailableUsers() {
        return USERS;
    }

    public static synchronized void logIn(int userId) {
        validateUserId(userId);
        loggedUserId = userId;
    }

    private static void validateUserId(int userId) {
        if (USERS.stream().noneMatch(u -> u.getId().equals(userId))) {
            throw new NotValidUserException(String.format("User with id %s is not registered", userId));
        }
    }

    public static synchronized int loggedUserId() {
        return loggedUserId;
    }
}
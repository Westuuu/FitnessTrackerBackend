package com.fitnesstrackerbackend.core.database;


import com.fitnesstrackerbackend.domain.user.model.UserType;
import org.springframework.util.Assert;

public class DatabaseContextHolder {

    private static final ThreadLocal<UserType> CONTEXT = new ThreadLocal<>();

    public static void setRole(UserType role) {
        Assert.notNull(role, "DB role can't be null");
        CONTEXT.set(role);
    }

    public static UserType getRole() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}

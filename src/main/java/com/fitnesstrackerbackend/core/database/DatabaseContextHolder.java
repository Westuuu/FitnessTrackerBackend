package com.fitnesstrackerbackend.core.database;


import org.springframework.util.Assert;

public class DatabaseContextHolder {

    private static final ThreadLocal<DbRole> CONTEXT = new ThreadLocal<>();

    public static void setRole(DbRole role) {
        Assert.notNull(role, "DB role can't be null");
        CONTEXT.set(role);
    }

    public static DbRole getRole() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}

package com.fitnesstrackerbackend.domain.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserType {
    @JsonProperty("admin")
    ADMIN,
    @JsonProperty("trainer")
    TRAINER,
    @JsonProperty("trainee")
    TRAINEE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

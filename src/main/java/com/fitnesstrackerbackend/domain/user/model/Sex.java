package com.fitnesstrackerbackend.domain.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Sex {
    @JsonProperty("male")
    MALE,
    @JsonProperty("female")
    FEMALE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

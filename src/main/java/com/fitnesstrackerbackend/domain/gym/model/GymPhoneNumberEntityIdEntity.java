package com.fitnesstrackerbackend.domain.gym.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class GymPhoneNumberEntityIdEntity implements Serializable {
    private static final long serialVersionUID = 6751213617068985941L;
    @NotNull
    @Column(name = "gymid", nullable = false)
    private Integer gymid;

    @Size(max = 15)
    @NotNull
    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;


}
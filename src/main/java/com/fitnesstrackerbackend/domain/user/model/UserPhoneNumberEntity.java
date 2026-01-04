package com.fitnesstrackerbackend.domain.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "user_phone_number")
public class UserPhoneNumberEntity {
    @EmbeddedId
    private UserPhoneNumberIdEntity id;

    @MapsId("userid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userid", nullable = false)
    private UserEntity userid;

    @Size(max = 4)
    @NotNull
    @Column(name = "country_prefix", nullable = false, length = 4)
    private String countryPrefix;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary;


}
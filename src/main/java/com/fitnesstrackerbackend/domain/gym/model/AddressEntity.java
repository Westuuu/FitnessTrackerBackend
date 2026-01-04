package com.fitnesstrackerbackend.domain.gym.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "address")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {
    @Id
    @Column(name = "gymid", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gymid", nullable = false)
    private GymEntity gym;

    @Size(max = 100)
    @NotNull
    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @Size(max = 9)
    @NotNull
    @Column(name = "post_code", nullable = false, length = 9)
    private String postCode;

    @Size(max = 100)
    @NotNull
    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Size(max = 100)
    @NotNull
    @Column(name = "street", nullable = false, length = 100)
    private String street;

    @Size(max = 16)
    @NotNull
    @Column(name = "street_number", nullable = false, length = 16)
    private String streetNumber;

    @Size(max = 16)
    @NotNull
    @Column(name = "apartment_number", nullable = false, length = 16)
    private String apartmentNumber;


}
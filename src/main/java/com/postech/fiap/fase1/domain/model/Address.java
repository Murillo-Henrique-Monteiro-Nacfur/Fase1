package com.postech.fiap.fase1.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name = "ADDRESS")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "STREET", nullable = false)
    private String street;

    @Column(name = "NUMBER", nullable = false)
    private String number;

    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "STATE", nullable = false, length = 2)
    private String state;

    @Column(name = "ZIP_CODE", nullable = false)
    private String zipCode;

    @Column(name = "COUNTRY", nullable = false)
    private String country;

    @Column(name = "NEIGHBORHOOD", nullable = false)
    private String neighborhood;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
}

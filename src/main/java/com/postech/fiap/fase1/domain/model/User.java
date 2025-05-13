package com.postech.fiap.fase1.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS", schema = "public")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "USERS_ID_seq", allocationSize = 1)
    @Column(name = "ID")
    private Long id;
    @Setter
    @Column(name = "NAME")
    private String name;
    @Setter
    @Column(name = "EMAIL")
    private String email;
    @Setter
    @Column(name = "LOGIN")
    private String login;
    @Setter
    @Column(name = "PASSWORD")
    private String password;
    @Setter
    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;
    @Setter
    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

}

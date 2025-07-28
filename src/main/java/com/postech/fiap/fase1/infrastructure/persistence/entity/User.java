package com.postech.fiap.fase1.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS", schema = "public")
public class User extends BaseEntity implements Addressable {
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

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "ADDRESSABLE_ID", insertable = false, updatable = false)
    @SQLRestriction("\"ADDRESSABLE_TYPE\" = 'User'")
    private List<Address> addresses = new ArrayList<>();

    public void setAddresses(List<Address> addresses) {
        addresses.forEach(address -> address.setAddressable(this));
        this.addresses = addresses;
    }
}

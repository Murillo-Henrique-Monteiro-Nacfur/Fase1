package com.postech.fiap.fase1.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "RESTAURANT", schema = "public")
public class Restaurant extends BaseEntity implements Addressable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "RESTAURANT_ID_seq", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Setter
    @Column(name = "NAME", nullable = false)
    private String name;
    @Setter
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
    @Setter
    @Column(name = "CUISINE_TYPE", nullable = false)
    private String cuisineType;
    @Setter
    @Column(name = "CNPJ", nullable = false)
    private String cnpj;
    @Setter
    @Column(name = "OPEN_TIME", nullable = false)
    private LocalTime openTime;

    @Setter
    @Column(name = "CLOSE_TIME", nullable = false)
    private LocalTime  closeTime;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "ADDRESSABLE_ID",insertable = false, updatable = false)
    @SQLRestriction("\"ADDRESSABLE_TYPE\" = 'Restaurant'")
    private List<Address> addresses = new ArrayList<>();
}

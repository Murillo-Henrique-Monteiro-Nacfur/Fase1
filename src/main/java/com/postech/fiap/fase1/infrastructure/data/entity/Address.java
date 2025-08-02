package com.postech.fiap.fase1.infrastructure.data.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name = "ADDRESS")
public class Address extends BaseEntity {
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

    @Any
    @AnyKeyJavaClass(Long.class)
    @AnyDiscriminator(DiscriminatorType.STRING)
    @AnyDiscriminatorValue(discriminator = "User", entity = User.class)
    @AnyDiscriminatorValue(discriminator = "Restaurant", entity = Restaurant.class)
    @Column(name = "ADDRESSABLE_TYPE")          // Nome da coluna que guarda a String ('User' ou 'Restaurant')
    @JdbcTypeCode(SqlTypes.VARCHAR)             // Mapeia para o tipo JDBC VARCHAR
    @JoinColumn(name = "ADDRESSABLE_ID")        // Nome da coluna que guarda o ID
    @NotFound(action = NotFoundAction.IGNORE)
    private Addressable addressable;

    @Column(name = "ADDRESSABLE_TYPE", insertable = false, updatable = false)
    private String addressableType; // Tipo do endereço, pode ser 'User' ou 'Restaurant'


}

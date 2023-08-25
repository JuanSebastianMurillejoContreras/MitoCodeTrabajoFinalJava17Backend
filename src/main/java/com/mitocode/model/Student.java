package com.mitocode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @EqualsAndHashCode.Include
    @Column(length = 50, unique = true, nullable = false)
    private String dni;

    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private int age;

    @EqualsAndHashCode.Include
    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    @EqualsAndHashCode.Include
    private String lastName;

}

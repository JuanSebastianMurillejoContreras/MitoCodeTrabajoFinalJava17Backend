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
public class EnrollDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEnrollDetail;

    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false, foreignKey = @ForeignKey(name = "FK_ENROLLDETAIL_COURSE"))
    private Course course;

    @ManyToOne
    @JoinColumn(name = "id_enroll", nullable = false, foreignKey = @ForeignKey(name = "FK_ENROLLDETAIL_ENROLL"))
    private Enroll enroll;

    private String classroom;
}

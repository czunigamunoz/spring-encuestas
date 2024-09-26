package com.jpa.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Represents a Survey entity in the database.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "survey")
public class Survey {

    /**
     * The unique identifier for the survey.
     */
    @Id
    private String id;

    /**
     * The title of the survey.
     */
    @Column(nullable = false)
    private String title;

    /**
     * The list of questions associated with this survey.
     * Cascade type ALL means that any operation on the survey will also be applied to the questions.
     */
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<Question> questions;
}

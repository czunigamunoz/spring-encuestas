package com.jpa.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Represents a Question entity in the database.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question")
public class Question {

    /**
     * The unique identifier for the question.
     */
    @Id
    private String id;

    /**
     * The text of the question.
     */
    @Column(nullable = false)
    private String questionText;

    /**
     * The survey to which this question belongs.
     * Ignored in JSON serialization to prevent infinite recursion.
     */
    @ManyToOne
    @JoinColumn(name = "survey_id")
    @JsonIgnoreProperties("questions")
    private Survey survey;

    /**
     * The list of responses associated with this question.
     * Cascade type ALL means that any operation on the question will also be applied to the responses.
     */
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Response> responses;
}

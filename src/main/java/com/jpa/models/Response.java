package com.jpa.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

/**
 * The list of responses associated with this question.
 * Cascade type ALL means that any operation on the question will also be applied to the responses.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    /**
     * The unique identifier for the response.
     */
    @Id
    private String id;

    /**
     * The text of the response.
     */
    @Column(nullable = false)
    private String answerText;

    /**
     * The question to which this response belongs.
     * Ignored in JSON serialization to prevent infinite recursion.
     */
    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonIgnoreProperties("responses")
    private Question question;
}

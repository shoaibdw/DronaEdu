package com.drona.drona.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;



/**
 * The `Assignment` class represents an assignment or task associated with a course batch.
 * Instructors can create assignments to be completed by students in a specific batch.
 */
@Data
@AllArgsConstructor
@Document(collection = "assignments")
public class Assignment {

    @Transient
    public static final String SEQUENCE_NAME = "assignment_sequence";

    @Id
    private int assignmentId;        // Unique identifier for the assignment.

    private String title;            // Title of the assignment.
    private String session;          // Session or course module to which the assignment belongs.

    @DBRef
    private Batch batch;             // Reference to the batch to which this assignment is associated.

    private String description;      // Description or instructions for the assignment.
    private String dueDate;          // Due date for completing the assignment.
    private double points;          // Points or score associated with the assignment.


    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentId=" + assignmentId +
                ", title='" + title + '\'' +
                ", session='" + session + '\'' +
                ", batch=" + batch.toString() +
                ", description='" + description + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", points=" + points +
                '}';
    }
}

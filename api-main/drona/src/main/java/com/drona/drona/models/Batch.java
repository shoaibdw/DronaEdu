package com.drona.drona.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;


/**
 * The `Batch` class represents a group of scheduled sessions within a course.
 * Instructors can create and manage batches, and each batch can be added to a specific course.
 * The pricing of the course may vary depending on the batch.
 */
@Data
@AllArgsConstructor
@Document(collection = "batches")
public class Batch {

    @Transient
    public static final String SEQUENCE_NAME = "batch_sequence";

    @Id
    private int batchId;            // Unique identifier for the batch.

    @DBRef
    private Course course;          // Reference to the associated course.

    private int sessionDuration;    // Duration of each session in minutes.
    private int numberOfSessions;   // Total number of sessions in the batch.

    // Price for group and private sessions
    private double groupBatchPrice;
    private double privateBatchPrice;

    @DBRef
    private List<Assignment> assignments; // List of assignments associated with the batch.

    public Batch() {
        assignments = new ArrayList<>();
    }


    @Override
    public String toString() {
        return "Batch{" +
                "batchId=" + batchId +
                ", course=" + course.toString() +
                ", sessionDuration=" + sessionDuration +
                ", numberOfSessions=" + numberOfSessions +
                ", groupBatchPrice=" + groupBatchPrice +
                ", privateBatchPrice=" + privateBatchPrice +
                //", assignments=" + assignments +
                '}';
    }
}

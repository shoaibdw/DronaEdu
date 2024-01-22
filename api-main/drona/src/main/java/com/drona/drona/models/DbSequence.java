package com.drona.drona.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



/**
 * The `DbSequence` class represents a document used to store sequences or counters in the MongoDB database.
 * It is typically used to generate unique identifiers for other MongoDB documents.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection=("db_sequence"))
public class DbSequence {

    @Id
    private String id;   // Unique identifier for the sequence document.
    private int seqNo;    // The current sequence number value.

}

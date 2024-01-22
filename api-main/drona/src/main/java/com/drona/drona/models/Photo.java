package com.drona.drona.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "images")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Photo {
    @Id
    private String id;
    private String title; // You can add additional fields, such as file name, content type, etc.
    private Binary photo;

}

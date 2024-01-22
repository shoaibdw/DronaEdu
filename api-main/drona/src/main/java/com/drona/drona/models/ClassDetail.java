package com.drona.drona.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "classDetail")
public class ClassDetail {
    private String projectTitle;
    private String concept;
}

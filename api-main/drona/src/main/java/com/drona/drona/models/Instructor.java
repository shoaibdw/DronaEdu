package com.drona.drona.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


/**
 * The `Instructor` class represents an instructor in the system.
 */

@Data
@AllArgsConstructor
@Document(collection = "instructors")
public class Instructor extends User {


    private double rating = 0; // The overall rating of the instructor.
    private String experience; // The instructor's years of experience.
    private boolean Graduated; // Indicates if the instructor is a graduate.
    private String about; // A brief description of the instructor.
    private List<String> language; // List of languages the instructor is proficient in.

    @DBRef
    private List<Course> createdCourses; // List of courses created by the instructor.

    private List<Double> ratings; // List of ratings given by students.
    private List<String> feedback; // List of feedback comments from students.
    private boolean approved = false; // Indicates if the instructor's is approved by the admin.


    /**
     * Default constructor for the `Instructor` class.
     */
    public Instructor() {

        createdCourses =new ArrayList<>();
        ratings=new ArrayList<>();
        feedback=new ArrayList<>();
    }

    /**
     * Generates a string representation of the `Instructor` object.
     *
     * @return A string containing information about the instructor, including their name, rating, experience,
     * graduation status, description, languages, and the number of courses they've created.
     */
    @Override
    public String toString() {
        return "Instructor :"+getUsername()+"\n"+"\n" +
                "rating: " + rating +
                ", experience: '" + experience + '\'' +
                ", Graduated:" + Graduated +
                ", about: " + about + '\'' +
                ", language: " + language +
                ", Totalcourses: " + createdCourses.size() +
                "\n"+"\n";
    }
}

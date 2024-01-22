package com.drona.drona.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The `Course` class represents a course offered in the system.
 */
@Data
@AllArgsConstructor
@Document(collection="courses")
public class Course {

    @Transient
    public static final String SEQUENCE_NAME="course_sequence";


    @Id
    private int courseId; // Unique identifier for the course.

    private String title; // Title or name of the course.
    private String description; // A brief description of the course.
    private int lessons; // Number of lessons in the course.
    private String duration; // Duration of the course.
    private double rating; // Average rating of the course.
    private String language; // Language in which the course is taught.
    private String category; // Category to which the course belongs.
    private String level; // Level of the course (e.g., beginner, intermediate,advanced).
    private String ageGroup; // Age group for which the course is suitable.

    private int numberOfClasses; // Number of classes in the course
    private List<ClassDetail> classDetails; // List of class details
    private String importance; // Importance of the course

    @DBRef
    private Photo image; // Image representing the course.
    //private double price;

    // Student-selected preferred date for the course.
    private LocalDate preferredDate;

    //student will set it
    @Field("bookingType")
    private BookingType bookingType; // Booking type for the course.

    @DBRef
    public Instructor instructor; // Instructor teaching the course.

    @DBRef
    private List<Batch> batches; // Batches associated with the course.

    @DBRef(lazy = true)
    private List<Student> students; // List of students enrolled in the course.


    /**
     * Default constructor for the `Course` class.
     */
    public Course() {
        batches =new ArrayList<>();
       students=new ArrayList<>();
    }

    @Override
    public String toString() {
        return "CourseId :" + courseId +" {" +
                '\'' +
                " courseName:'" + title + '\'' +
                ", description:'" + description + '\'' +
                //", price:" + price +
                ", lessons:" + lessons +
                ", duration:'" + duration + '\'' +
                ", rating:" + rating +
                ", language:'" + language + '\'' +
                ", Category:'" + category + '\'' +
                //", instructor:" + instructor.getUsername() +'\'' +
                ", level:" + level+'\'' +
                '}'+"\n"+"\n";
    }
}

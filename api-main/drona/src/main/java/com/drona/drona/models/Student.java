package com.drona.drona.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


/**
 * The `Student` class represents a student entity, inheriting user-related properties and behaviors.
 * This class is used for modeling students within the system.
 */

@Data
@AllArgsConstructor
@Document(collection = "students")
public class Student extends User{

    /**
     * List of courses that the student is registered for.
     * The `@DBRef` annotation establishes a reference to other documents and 'lazy' is set to true for lazy loading.
     */
    @DBRef(lazy = true)
    private List<Course> registeredCourses;

    /**
     * The coupon code used by the student, if any.
     */
    private String usedCouponCode;

    public Student() {
        registeredCourses = new ArrayList<>();

    }

    /**
     * Get a string representation of the Student object.
     *
     * @return A string representation including the student's username.
     */
    @Override
    public String toString() {
        return "Student{" +
                getUsername() +
                '}';
    }
}

package com.drona.drona.repositories;

import com.drona.drona.models.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


/**
 * This repository interfaces with the database to perform CRUD operations on the 'Student' entity.
 */
@Repository
public interface StudentRepository extends MongoRepository<Student,String> {


    /**
     * Retrieve a student by their username.
     *
     * @param username The username of the student.
     * @return An Optional containing the student, if found, or an empty Optional.
     */
    Optional<Student> findStudentByUsername(String username);


    /**
     * Retrieve a student by their email address.
     *
     * @param email The email address of the student.
     * @return An Optional containing the student, if found, or an empty Optional.
     */
    Optional<Student> findStudentByEmail(String email);
}
package com.drona.drona.repositories;

import com.drona.drona.models.Instructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;


/**
 * Repository for interacting with the "instructors" collection in the MongoDB database.
 */
public interface InstructorRepository extends MongoRepository<Instructor,String> {

    /**
     * Find an instructor by their username.
     *
     * @param username The username to search for.
     * @return An optional containing the instructor if found, or empty if not found.
     */
    Optional<Instructor> findInstructorByUsername(String username);

    /**
     * Find an instructor by their email address.
     *
     * @param email The email address to search for.
     * @return An optional containing the instructor if found, or empty if not found.
     */
    Optional<Instructor> findInstructorByEmail(String email);


    /**
     * Find all unapproved instructors.
     *
     * @return A list of unapproved instructors.
     */
    List<Instructor>findAllByApprovedTrue();

    List<Instructor> findAllByApprovedFalse();



}

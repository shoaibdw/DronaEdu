package com.drona.drona.repositories;

import com.drona.drona.models.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;


/**
 * Repository for interacting with the "admin" collection in the MongoDB database.
 */
public interface AdminRepository extends MongoRepository<Admin,String> {


    /**
     * Find an admin by their username.
     *
     * @param username The username to search for.
     * @return An optional containing the admin if found, or empty if not found.
     */
    Optional<Admin> findAdminByUsername(String username);

    /**
     * Find an admin by their email address.
     *
     * @param email The email address to search for.
     * @return An optional containing the admin if found, or empty if not found.
     */
    Optional<Admin> findAdminByEmail(String email);
}

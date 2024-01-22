package com.drona.drona.repositories;

import com.drona.drona.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * does the job of talking to the database and getting the data back
 *
 *
 */
@Repository

public interface UserRepository extends MongoRepository<User,String>{
    Optional<User> findUserByEmail(String email);
    //Optional<User>  findUserByUsername(String username);


}

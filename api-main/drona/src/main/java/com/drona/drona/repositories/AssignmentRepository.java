package com.drona.drona.repositories;

import com.drona.drona.models.Assignment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends MongoRepository<Assignment,Integer> {
}

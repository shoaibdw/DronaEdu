package com.drona.drona.repositories;

import com.drona.drona.models.Batch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Repository for interacting with the "batches" collection in the MongoDB database.
 */
@Repository
public interface BatchRepository extends MongoRepository<Batch,Integer> {


    /**
     * Find a batch by its batch ID.
     *
     * @param batchId The batch ID to search for.
     * @return An optional containing the batch if found, or empty if not found.
     */
    Optional<Batch> findBatchByBatchId(int batchId);
}

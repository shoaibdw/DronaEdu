package com.drona.drona.services;


import com.drona.drona.models.DbSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;


/**
 * Service for generating unique sequence numbers.
 */
@Service
public class SequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;


    /**
     * Get the next sequence number for the given sequence name.
     *
     * @param sequenceName The name of the sequence.
     * @return The next sequence number.
     */
    public int getSequenceNumber(String sequenceName) {

        // Create a query to find the document with the specified sequence name.
        //get sequence no
        Query query = new Query(Criteria.where("id").is(sequenceName));
        // Update the sequence number by incrementing it.
        Update update = new Update().inc("seqNo", 1);

        // Find and modify the document with the given sequence name.
        DbSequence counter = mongoOperations
                .findAndModify(query,
                        update, options().returnNew(true).upsert(true),
                        DbSequence.class);

        // Return the next sequence number. If the counter doesn't exist, start from 1.
        return !Objects.isNull(counter) ? counter.getSeqNo(): 1;
    }

}

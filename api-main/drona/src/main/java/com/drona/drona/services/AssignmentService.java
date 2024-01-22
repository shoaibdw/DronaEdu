package com.drona.drona.services;


import com.drona.drona.models.Assignment;
import com.drona.drona.models.Batch;
import com.drona.drona.repositories.AssignmentRepository;
import com.drona.drona.repositories.BatchRepository;
import org.bson.codecs.BsonArrayCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


/**
 * Service class for managing assignments and related operations.
 */
@Service
public class AssignmentService {


    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private BatchRepository batchRepository;

    /**
     * Add a new assignment to a batch.
     *
     * @param assignment The assignment to be added.
     * @param batchID    The ID of the batch to which the assignment will be added.
     */
    public void addAssignment(Assignment assignment,int batchID)
    {

        Batch batch=batchRepository.findBatchByBatchId(batchID)
                .orElseThrow(() -> new NoSuchElementException("Batch not found with ID: " + batchID));

        assignment.setAssignmentId(sequenceGeneratorService.getSequenceNumber(Assignment.SEQUENCE_NAME));
        assignment.setBatch(batch);
        batch.getAssignments().add(assignment);
        assignmentRepository.save(assignment);
        batchRepository.save(batch);
    }
}

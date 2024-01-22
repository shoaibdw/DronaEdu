package com.drona.drona.services;

import com.drona.drona.models.Batch;
import com.drona.drona.models.Course;
import com.drona.drona.repositories.BatchRepository;
import com.drona.drona.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;



/**
 * Service class for managing batches and related operations.
 */
@Service
public class BatchService {


    @Autowired
    CourseRepository courseRepository;
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    /**
     * Add a new batch to a course.
     *
     * @param batch    The batch to be added.
     * @param courseID The ID of the course to which the batch will be added.
     */
    public void addBatch(Batch batch,int courseID)
    {
        Course course=courseRepository.findCourseByCourseId(courseID)
                .orElseThrow(() -> new NoSuchElementException("No course with this id exists: " + courseID));

        // Generate a unique batch ID using the sequence generator service.
        batch.setBatchId(sequenceGeneratorService.getSequenceNumber(Batch.SEQUENCE_NAME));
        batch.setCourse(course);

        // Calculate batch prices based on the number of sessions.
        batch.setGroupBatchPrice(batch.getNumberOfSessions()*299);
        batch.setPrivateBatchPrice(batch.getNumberOfSessions()*499);
        course.getBatches().add(batch);

        // Save the batch and course entities in the repository.
        batchRepository.save(batch);
        courseRepository.save(course);

    }
}

package com.drona.drona.services;


import com.drona.drona.models.Batch;
import com.drona.drona.models.Course;
import com.drona.drona.models.Instructor;
import com.drona.drona.models.Student;
import com.drona.drona.repositories.BatchRepository;
import com.drona.drona.repositories.InstructorRepository;
import com.drona.drona.repositories.StudentRepository;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;



/**
 * Service for handling administrative tasks.
 */
@Service
public class AdminService {


    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private BatchRepository batchRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    MongoClient mongoClient;

    @Autowired
    MongoConverter converter;



    /**
     * Get a list of pending instructors awaiting approval.
     *
     * @return A list of pending instructors.
     */
    public List<Instructor> getPendingInstructors ()
    {
           return instructorRepository.findAllByApprovedFalse();

    }

    /**
     * Approve an instructor's account by username.
     *
     * @param username The username of the instructor to approve.
     */
    public void approveInstructor(String username)
    {
        Instructor instructor=instructorRepository.findInstructorByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Instructor not found with username: " + username));
        instructor.setApproved(true);
        instructorRepository.save(instructor);
    }

    /**
     * Decline an instructor's account by username.
     *
     * @param username The username of the instructor to decline.
     */
    public void declineInstructor(String username)
    {
        Instructor instructor=instructorRepository.findInstructorByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Instructor not found with username: " + username));
      instructorRepository.delete(instructor);
    }

    /**
     * Get a list of all students.
     *
     * @return A list of all students.
     */
    public List<Student> getStudents()
    {
        return studentRepository.findAll();
    }


    /**
     * Find students by a field and sort them.
     *
     * @param field The field to search for.
     * @param sort  The sorting order.
     * @return A list of students matching the search criteria.
     */
    public List<Student> findStudentsByField(String field,String sort) {
        final List<Student> students = new ArrayList<>();
        MongoDatabase database = mongoClient.getDatabase("DronaDB");
        MongoCollection<Document> collection = database.getCollection("students");

        // Create the regex pattern to perform partial match search
        Pattern regexPattern = Pattern.compile(".*" + field + ".*", Pattern.CASE_INSENSITIVE);

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(
                new Document("$match",
                        new Document("$or", Arrays.asList(

                                new Document("id", regexPattern),
                                new Document("username", regexPattern),
                                new Document("email", regexPattern)
                                )
                        )),
                new Document("$sort", new Document(sort, 1L))
                )
        );

        result.forEach(doc ->students.add(converter.read(Student.class, doc)));

        return students;
    }

    /**
     * Find instructors by a field and sort them.
     *
     * @param field The field to search for.
     * @param sort  The sorting order.
     * @return A list of instructors matching the search criteria.
     */
    public List<Instructor> findInstructorsByField(String field,String sort) {
        final List<Instructor> instructors = new ArrayList<>();
        MongoDatabase database = mongoClient.getDatabase("DronaDB");
        MongoCollection<Document> collection = database.getCollection("instructors");

        // Create the regex pattern to perform partial match search
        Pattern regexPattern = Pattern.compile(".*" + field + ".*", Pattern.CASE_INSENSITIVE);

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(
                        new Document("$match",
                                new Document("$or", Arrays.asList(

                                        new Document("id", regexPattern),
                                        new Document("username", regexPattern),
                                        new Document("email", regexPattern)
                                )
                                )),
                        new Document("$sort", new Document(sort, 1L))
                )
        );

        result.forEach(doc ->instructors.add(converter.read(Instructor.class, doc)));


        for(Instructor i:instructors)
        {
            if(!i.isApproved())
                instructors.remove(i);

        }
        return instructors;
    }


    /**
     * Get a list of all batches.
     *
     * @return A list of all batches.
     */
    public List<Batch> getBatches()
    {
        return batchRepository.findAll();
    }


//    public List<Student> searchStudents(String id, String name, String email, String courseTitle) {
//        // Create a criteria object to build the search query
//        Criteria searchCriteria = new Criteria();
//
//        if (id != null) {
//            searchCriteria.orOperator(Criteria.where("id").is(id));
//        }
//
//        if (name != null) {
//            searchCriteria.orOperator(Criteria.where("name").regex(name, "i")); // Case-insensitive search
//        }
//
//        if (email != null) {
//            searchCriteria.orOperator(Criteria.where("email").regex(email, "i")); // Case-insensitive search
//        }
//
//        if (courseTitle != null) {
//            searchCriteria.and("registeredcourses.title").regex(courseTitle, "i"); // Case-insensitive search
//        }
//
//        // Create a query object with the search criteria
//        Query query = new Query(searchCriteria);
//
//        // Execute the query and return the matching students
//        return studentRepository.find(query);
//    }
}







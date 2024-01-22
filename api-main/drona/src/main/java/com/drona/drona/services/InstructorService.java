package com.drona.drona.services;

import com.drona.drona.models.Course;
import com.drona.drona.models.Instructor;
import com.drona.drona.repositories.CourseRepository;
import com.drona.drona.repositories.InstructorRepository;
import com.drona.drona.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;



/**
 * Service class for handling instructor-related operations.
 */
@Service
public class InstructorService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private PhotoService photoService;


    /**
     * Add a new course for an instructor.
     *
     * @param username The username of the instructor who is adding the course.
     * @param course   The course to be added.
     * @return A response entity indicating the success or failure of the operation.
     */
    public ResponseEntity<String> addCourse(String username, Course course, MultipartFile image) throws IOException {
        // Generate a unique course ID using the sequence generator service.
        course.setCourseId(sequenceGeneratorService.getSequenceNumber(Course.SEQUENCE_NAME));

        // Retrieve the instructor by username or throw an exception if not found.
        Instructor instructor=instructorRepository.findInstructorByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Instructor not found with username: " + username));

        if(!instructor.isApproved())return new ResponseEntity<>("Instructor not approved", HttpStatus.INTERNAL_SERVER_ERROR);
        // Check if a course with the same title already exists.
        boolean courseExists=courseRepository.findCourseByTitle(course.getTitle()).isPresent();

        if(courseExists)
            return new ResponseEntity<>("Course with this title already exists", HttpStatus.INTERNAL_SERVER_ERROR);

        // Generate a unique course ID using the sequence generator service.
        course.setCourseId(sequenceGeneratorService.getSequenceNumber(Course.SEQUENCE_NAME));

        // Set the instructor for the course and update the instructor's list of created courses.
        course.setInstructor(instructor);

        //adding photo
        photoService.addPhoto(image.getOriginalFilename(),image);
        instructor.getCreatedCourses().add(course);

        // Save the changes to the database.
        instructorRepository.save(instructor);
        courseRepository.save(course);
        //instructorRepository.findAll().toString();
        return new ResponseEntity<>(course.getTitle() + " course is added successfully", HttpStatus.OK);
    }

}

package com.drona.drona.controllers;


import com.drona.drona.models.*;
import com.drona.drona.repositories.InstructorRepository;
import com.drona.drona.services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private InstructorRepository instructorRepository;



    @ApiIgnore
    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    /**
     * Instructor can add courses.
     *
     * @param username The username of the instructor.
     * @param course   The course to be added.
     * @return A response entity indicating the success or failure of the operation.
     */
    @PostMapping("/addCourse/{username}")
    public ResponseEntity<String>addCourse(@PathVariable String username, @RequestBody Course course,@RequestParam("image") MultipartFile image) throws IOException {

            return instructorService.addCourse(username,course,image);

    }

    /**
     * Get the list of courses created by an instructor.
     *
     * @param username The username of the instructor.
     * @return A response entity containing the list of created courses.
     */
    @GetMapping("/getInstructorCourses/{username}")
    public ResponseEntity<List<Course>> getInstructorCourses(@PathVariable String username) {

        Instructor instructor=instructorRepository.findInstructorByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Instructor not found with username: " + username));

        if(!instructor.isApproved()) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        // Return the list of created courses in the response
        return new ResponseEntity<>( instructor.getCreatedCourses(), HttpStatus.OK);
    }


    /**
     * Get dashboard statistics for an instructor, including enrollments, batches, and courses.
     *
     * @param username The username of the instructor.
     * @return A response entity containing the dashboard statistics.
     */
    @GetMapping("/dashboard/{username}")
    public ResponseEntity<Object> getDashboard(@PathVariable String username)
    {
        Instructor instructor=instructorRepository.findInstructorByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Instructor not found with username: " + username));
        if(!instructor.isApproved())return new ResponseEntity<>("Instructor not approved", HttpStatus.INTERNAL_SERVER_ERROR);
        int enrollments=0;
        int batches=0;
        int courses=instructor.getCreatedCourses().size();

        for (Course course : instructor.getCreatedCourses()) {
            // Calculate enrollments and batches.
            batches += course.getBatches().size();
            enrollments += course.getStudents().size();
        }

        String dashboardInfo = "Enrollments: " + enrollments + "\n" +
                "Batches: " + batches + "\n" +
                "Courses: " + courses + "\n";



        return new ResponseEntity<>(dashboardInfo,HttpStatus.OK);

    }

    /**
     * Get a list of batches for an instructor.
     *
     * @param username The username of the instructor.
     * @return A response entity containing the list of batches.
     */
    @GetMapping("/allBatches/{username}")
    public ResponseEntity<List<Batch>> getBatches(@PathVariable String username) {
        Instructor instructor = instructorRepository.findInstructorByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Instructor not found with username: " + username));
        if(!instructor.isApproved())return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        List<Batch> batches = new ArrayList<>();

        for (Course course : instructor.getCreatedCourses()) {
            // Collect all batches from the instructor's courses.
            batches.addAll(course.getBatches());
        }
        // Return the list of batches in the response.
        return new ResponseEntity<>(batches, HttpStatus.OK);
    }

    /**
     * Get feedback for an instructor.
     *
     * @param username The username of the instructor.
     * @return A response entity containing instructor feedback.
     */
    @GetMapping("/feedBack/{username}")
    public ResponseEntity<List<String>> getFeedBack(@PathVariable String username)
    {

        Instructor instructor=instructorRepository.findInstructorByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Instructor not found with username: " + username));
        if(!instructor.isApproved())return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        // Return the list of feedback in the response.
        return new ResponseEntity<>(instructor.getFeedback(), HttpStatus.OK);

    }


    /**
     * Get the list of students associated with an instructor& search through them.
     *
     * @param username     The username of the instructor.
     * @param searchQuery  An optional search query to filter students by username.
     * @return A response entity containing the list of students.
     */
    @GetMapping("/students/{username}")
    public ResponseEntity<List<Student>> getStudents(@PathVariable String username,
                                                    @RequestParam(required = false) String searchQuery) {
        Instructor instructor = instructorRepository.findInstructorByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Instructor not found with username: " + username));

        if(!instructor.isApproved())return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        List<Student> students = new ArrayList<>();

        for (Course course : instructor.getCreatedCourses()) {
            List<Student> courseStudents = course.getStudents();

            // If a search query is provided, filter the students based on the search criteria
            if (searchQuery != null && !searchQuery.isEmpty()) {
                courseStudents = courseStudents.stream()
                        .filter(student -> student.getUsername().contains(searchQuery))
                        .collect(Collectors.toList());
            }

            // Collect the students for the response.
            students.addAll(courseStudents);
        }


        return ResponseEntity.ok(students);
    }







    }





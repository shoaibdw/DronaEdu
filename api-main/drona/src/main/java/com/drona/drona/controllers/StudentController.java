package com.drona.drona.controllers;

import com.drona.drona.models.*;
import com.drona.drona.models.dto.StudentDto;
import com.drona.drona.repositories.CourseRepository;
import com.drona.drona.repositories.InstructorRepository;
import com.drona.drona.repositories.StudentRepository;
import com.drona.drona.repositories.UserRepository;
import com.drona.drona.services.CourseService;
import com.drona.drona.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;





    @ApiIgnore
    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    /**
     * Register a student for a course.
     *
     * @param username   The username of the student.
     * @param courseId   The ID of the course to register for.
     * @param bookingType   The type of booking for the course.
     * @param date   The preferred date for the course (optional).
     * @param couponCode   The coupon code to apply (optional).
     * @return Registration result.
     */
    @PostMapping("/register-course")
    public String registerCourse(@RequestParam String username, @RequestParam int courseId,
                                 @RequestParam BookingType bookingType, @RequestParam(defaultValue = "2023-09-09") String date,
                                 @RequestParam(required = false) String couponCode)
    {

        return studentService.registerCourse(username,courseId,bookingType,date,couponCode);
    }

    /**
     * Get courses by rating within a specified range.
     *
     * @param min   The minimum rating value.
     * @param max   The maximum rating value.
     * @return Courses with ratings in the specified range.
     */
    @GetMapping("/courses/rating")
    public List<Course> getCoursesByRating(@RequestParam(value="min",defaultValue="0") double min,
                                           @RequestParam(value="max",defaultValue="5")double max)
    {
        return courseRepository.findCoursesByRatingBetween(min,max);
    }


    /**
     * Get courses registered by a specific student.
     *
     * @param username The username of the student.
     * @return List of courses registered by the student.
     */
    @GetMapping("/courses/{username}")
    public ResponseEntity<List<Course>> getStudentCourses(@PathVariable String username) {

        Student student=studentRepository.findStudentByUsername(username)
                .orElse(null);

        if (student == null) {
            // Return a 404 response indicating that the student was not found.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(student.getRegisteredCourses(), HttpStatus.OK);
        }



    /**
     * Search for courses by filtering based on field, rating range, sorting, and limiting results.
     *
     * @param field The field to filter on (e.g., "math", "science").
     * @param min The minimum rating value.
     * @param max The maximum rating value.
     * @param sort The sorting criteria (e.g., "title", "rating").
     * @param limit The maximum number of results to return.
     * @return Filtered and sorted list of courses.
     */
    @GetMapping("/courses/search")
    public List<Course> searchByFilter(@RequestParam String field, @RequestParam(defaultValue = "0") double min,
                                 @RequestParam(defaultValue = "5") double max, @RequestParam(defaultValue = "title") String sort,
                                 @RequestParam(defaultValue = "100") int limit)
    {

        return courseService.findByFieldAndRatingRange(field,min,max,sort,limit);
    }


    /**
     * Get instructor details for a specific course.
     *
     * @param courseId The ID of the course to retrieve instructor details.
     * @return Instructor details for the specified course.
     */
    @GetMapping("/courses/{courseId}/instructor")
    public ResponseEntity<Instructor> getSpecificInstructorDetails(@PathVariable int courseId)
    {
        Course course=courseRepository.findCourseByCourseId(courseId)
                .orElseThrow(() -> new NoSuchElementException("No course with this id available: " + courseId));

            return new ResponseEntity<>(course.getInstructor(),HttpStatus.OK);
    }

    /**
     * Get a list of all instructors.
     *
     * @return A list of all instructors.
     */
    @GetMapping("/instructors/all")
    public ResponseEntity<List<Instructor>> getAllInstructors()
    {
       return new ResponseEntity<>(studentService.getAllInstructors(),HttpStatus.OK);


    }

    /**
     * Get details of a specific instructor by username.
     *
     * @param username The username of the instructor.
     * @return Details of the specified instructor.
     */
    @GetMapping("/instructors/{username}")
    public ResponseEntity<Instructor> getInstructorDetails(@PathVariable String username)
    {
        Instructor instructor=instructorRepository.findInstructorByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Instructor not found with username: " + username));
        return new ResponseEntity<>(instructor,HttpStatus.OK);
    }



    /**
     * Rate an instructor by providing a rating value.
     *
     * @param rating The rating value to assign to the instructor.
     * @param instructorName The username of the instructor to rate.
     * @return A success response.
     */
    @PutMapping("/instructors/rate")
    public ResponseEntity<String> giveRating(@RequestParam double rating, @RequestParam String instructorName) {
        return studentService.giveRating(rating, instructorName);
    }

    /**
     * Provide feedback for an instructor.
     *
     * @param feedback The feedback text to provide.
     * @param instructorName The username of the instructor to provide feedback to.
     * @return A success response.
     */
    @PutMapping("/instructors/feedback")
    public ResponseEntity<String> giveFeedback(@RequestParam String feedback, @RequestParam String instructorName) {
        studentService.giveFeedback(feedback, instructorName);
        return studentService.giveFeedback(feedback, instructorName);
    }


    //for test
    @GetMapping("/getStudent/{username}")
    public StudentDto getStudent(@PathVariable String username)
    {
        Student student=studentRepository.findStudentByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("No student with this name exists " + username));
        return StudentDto.fromEntityToDto(student);

    }













//    @GetMapping("/SortByRating/{min}/{max}")
//    public String sortByRating(@PathVariable double min,@PathVariable double max)
//    {
//        return courseRepository.findCoursesByRatingIsGreaterThanEqualAndRatingIsLessThanEqual(min,max).toString();
//    }


}

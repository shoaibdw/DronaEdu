package com.drona.drona.controllers;

import com.drona.drona.models.Course;
import com.drona.drona.models.Student;
import com.drona.drona.repositories.CourseRepository;
import com.drona.drona.repositories.StudentRepository;
import com.drona.drona.repositories.UserRepository;
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
@RequestMapping("/api/courses")
public class CourseController {


    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;


    /**
     * Redirect to the Swagger documentation page.
     */
    @ApiIgnore
    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    /**
     * Get a list of all available courses.
     *
     * @return A response entity containing the list of courses or a message if none are available.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses ()
    {
        List<Course> courses=courseRepository.findAll();

        if (courses.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(courses,HttpStatus.OK);

    }


    /**
     * Get a course by its title.
     *
     * @param courseTitle The title of the course to retrieve.
     * @return A response entity containing the course information or an error message if not found.
     */
    @GetMapping("/byTitle/{courseTitle}")
    public ResponseEntity<Course> getCourseByTitle (@PathVariable String courseTitle)
    {
        Course course=courseRepository.findCourseByTitle(courseTitle)
                .orElseThrow(() -> new NoSuchElementException("No course with this title available: " + courseTitle));

        return new ResponseEntity<>(course, HttpStatus.OK);


    }

    /**
     * Get courses by their category.
     *
     * @param category The category to filter courses.
     * @return A response entity containing the list of courses in the specified category.
     */
    @GetMapping("/byCategory/{category}")
    public ResponseEntity<List<Course>> getCoursesByCategory (@PathVariable String category)
    {
        List<Course> courses = courseRepository.findCoursesByCategory(category);

        if (courses.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(courses, HttpStatus.OK);

    }


    /**
     * Get courses by their level.
     *
     * @param level The level to filter courses.
     * @return A response entity containing the list of courses at the specified level.
     */
    @GetMapping("/byLevel/{level}")
    public ResponseEntity<Object> getCoursesByLevel (@PathVariable String level)
    {
        List<Course> courses = courseRepository.findCoursesByLevel(level);

        if (courses.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(courses, HttpStatus.OK);

    }



    /**
     * Get the booking type and preferred date of a course for a student.
     *
     * @param username The username of the student.
     * @param courseId The ID of the course.
     * @return A response entity containing the course's booking type and preferred date or a message if not available.
     */
    //for testing
    @GetMapping("/getUserCoursesWithBookingType/{username}/{courseId}")
    public ResponseEntity<String> getCoursesBookingTypeAndDate(@PathVariable String username,@PathVariable int courseId) {
        Student student = studentRepository.findStudentByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("No user with this name exists: " + username));
        Course course=courseRepository.findCourseByCourseId(courseId)
                .orElseThrow(() -> new NoSuchElementException("No course with this id available: " + courseId));

        if(student.getRegisteredCourses().isEmpty())
            return new ResponseEntity<>("No courses available yet",HttpStatus.NO_CONTENT);


        return new ResponseEntity<>( course.getBookingType().toString()+"    "+course.getPreferredDate().toString(), HttpStatus.OK);
    }

//    @PostMapping("/addCourse")
//    public Course addCoursee(@RequestBody Course course)
//    {
//        //User user=userRepository.findUserByUsername(username);
//        //user.setUserRole(UserRole.INSTRUCTOR);
//        //course.setInstructor(user);
//        //ser.getCourses().add(course);
//        //userRepository.save(user);
//        return courseRepository.save(course);
//    }
//    @PostMapping("/search")
//    public List<Course> search(@RequestParam String searchQuery, @RequestParam String sortField, @RequestParam Integer limit) {
//        // Create a MongoDB query object.
//        Query query = new Query();
//
//        // Add the search query to the query object.
//        query.addCriteria(Criteria.where("title").regex(searchQuery, "i"));
//
//        // Add the sort field to the query object.
//        //query.withSort(Sort.by(Sort.Direction.ASC, sortField));
//
//        // Add the limit to the query object.
//        query.limit(limit);
//
//        // Find the documents in the database that match the query.
//        List<Course> courses = mongoTemplate.findAll(query);
//
//        // Return the results of the search.
//        return courses;
//    }
//
//    @GetMapping("/getUserCourses/{username}")
//    public ResponseEntity<String> getStudentCourses(@PathVariable String username) {
//
//       User user=userRepository.findUserByUsername(username).get();
//        if (user  == null) {
//            return new ResponseEntity<>("No user with this name exists",HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        else if
//        {
//            if (user instanceof )
//            {
//                if(user.getRegisteredcourses().isEmpty())
//                    return new ResponseEntity<>("No courses available yet",HttpStatus.OK);
//            }
//
//        }
//
//
//
//        return new ResponseEntity<>( student.getRegisteredcourses().toString(), HttpStatus.OK);
//    }

}

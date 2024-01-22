package com.drona.drona.services;


import com.drona.drona.models.*;
import com.drona.drona.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;



/**
 * Service for handling student-related operations.
 */
@Service
public class StudentService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private CouponRepository couponRepository;


    /**
     * Register a student for a course.
     *
     * @param username   The username of the student.
     * @param courseId   The ID of the course to register for.
     * @param bookingType The booking type for the course.
     * @param date       The preferred date for the course.
     * @param couponCode The coupon code to apply for the course.
     * @return A message indicating the result of the registration.
     */
    public String registerCourse(String username,int courseId, BookingType bookingType,String date,String couponCode)
    {
        // Find the student by username
        Student student=studentRepository.findStudentByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("No student with this name exists " + username));
        System.out.println( student.toString());

        // Find the course by courseId
        Course course=courseRepository.findCourseByCourseId(courseId)
                .orElseThrow(() -> new NoSuchElementException("No course with this id available: " + courseId));


        // Check if the student is already registered for this course
        for(int i = 0; i< student.getRegisteredCourses().size(); i++)
        {
            if( student.getRegisteredCourses().get(i).getCourseId()==course.getCourseId())
                return "Course "+course.getTitle()+" already registered";

        }
        // Set booking type and preferred date for the course
        course.setBookingType(bookingType);
        if (!date.isEmpty()) {
            LocalDate localDate = LocalDate.parse(date);
            course.setPreferredDate(localDate);
        }

        //TODO:let student pay without coupon
        if(couponCode==null)
        {
            student.getRegisteredCourses().add(course);
            course.getStudents().add(student);


            // Save changes to the repositories
            studentRepository.save(student);
            courseRepository.save(course);
            return course.getTitle()+" is added to Successfully";
        }

        else
        {
        // Check if the coupon code is valid and not already used by the student
        Coupon coupon=couponRepository.findCouponByCouponCode(couponCode)
                .orElseThrow(() -> new NoSuchElementException("Invalid coupon code: " + couponCode));

        if(student.getUsedCouponCode()!=null)
            return "You have already used a coupon code: " + student.getUsedCouponCode();

        if (coupon.getRedemptionLimit()<=0)
            return "Coupon code " + couponCode + " has reached its redemption limit";

        // TODO
        // Apply the coupon discount if it's valid
        // Assuming you have a method to calculate the discounted price




        // Update student's used coupon code and add the course
        student.setUsedCouponCode(couponCode);
        student.getRegisteredCourses().add(course);
        course.getStudents().add(student);


        // Save changes to the repositories
        studentRepository.save(student);
        courseRepository.save(course);

        // Increment the timesUsed count for the coupon
        coupon.setTimesUsed(coupon.getTimesUsed() + 1);
        couponRepository.save(coupon);

        return course.getTitle()+" is added to Successfully";}
    }


    /**
     * Get a list of all instructors.
     *
     * @return ResponseEntity containing the list of instructors.
     */

    public List<Instructor> getAllInstructors()
    {
        return instructorRepository.findAllByApprovedTrue();
    }

    /**
     * Give a rating to an instructor.
     *
     * @param rating        The rating to give.
     * @param instructorName The name of the instructor.
     */
    public ResponseEntity<String> giveRating(double rating,String instructorName)
    {

        //Student student=studentRepository.findStudentByUsername(studentName);

        // Find the instructor by username
        Instructor instructor=instructorRepository.findInstructorByUsername(instructorName)
                .orElseThrow(() -> new NoSuchElementException("Instructor not found with username: " + instructorName));

        if(!instructor.isApproved())return new ResponseEntity<>("Instructor not approved", HttpStatus.INTERNAL_SERVER_ERROR);
        // Add the rating to the instructor and update their overall rating
        instructor.getRatings().add(rating);
        double rate=0;
        for (double r : instructor.getRatings()) {
            rate += r;
        }
        instructor.setRating((rate/(instructor.getRatings().size())));

        // Save the updated instructor
        instructorRepository.save(instructor);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    /**
     * Provide feedback for an instructor.
     *
     * @param feedback       The feedback to provide.
     * @param instructorName The name of the instructor.
     */
    public ResponseEntity<String>  giveFeedback(String feedback,String instructorName)
    {
        // Find the instructor by username
        Instructor instructor=instructorRepository.findInstructorByUsername(instructorName)
                .orElseThrow(() -> new NoSuchElementException("Instructor not found with username: " + instructorName));
        if(!instructor.isApproved())return new ResponseEntity<>("Instructor not approved", HttpStatus.INTERNAL_SERVER_ERROR);
        // Add the feedback to the instructor
        instructor.getFeedback().add(feedback);

        // Save the updated instructor
        instructorRepository.save(instructor);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
//        List<User> users=userRepository.findAll();
//        List<User> instructors=new ArrayList<>();
//        for(int i=0;i<users.size();i++)
//        {
//            if(users.get(i).getUserRole()== UserRole.INSTRUCTOR)
//            {
//                instructors.add(users.get(i));
//            }
//        }
//        System.out.println(instructorRepository.findAll().toString());
//        return new ResponseEntity<>(instructors.toString(), HttpStatus.OK);
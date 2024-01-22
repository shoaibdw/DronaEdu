package com.drona.drona.controllers;


import com.drona.drona.models.Batch;
import com.drona.drona.models.Instructor;
import com.drona.drona.models.Student;
import com.drona.drona.repositories.StudentRepository;
import com.drona.drona.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoOperations;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {


    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MongoOperations mongoOperations;

    /**
     * Get a list of pending instructors awaiting approval.
     *
     * @return A list of pending instructors.
     */
    @GetMapping("/pendingInstructors")
    public List<Instructor> getPendingInstructors() {
        return adminService.getPendingInstructors();
    }

    /**
     * Approve an instructor's account by username.
     *
     * @param username The username of the instructor to approve.
     */
    @PutMapping("/approve/{username}")
    public void approveInstructor(@PathVariable("username") String username) {

        adminService.approveInstructor(username);
    }



    /**
     * Decline an instructor's account by username.
     *
     * @param username The username of the instructor to decline.
     */
    @DeleteMapping("/decline/{username}")
    public void declineInstructor(@PathVariable("username") String username) {

        adminService.declineInstructor(username);
    }

    /**
     * Search and retrieve students based on a specific field and sorting order.
     *
     * @param field The field to search for.
     * @param sort  The sorting order (default: "_id").
     * @return A string representation of the found students.
     */
    @GetMapping("/getStudents/{field}")
    public List<Student> getStudents(@PathVariable String field,@RequestParam(value="sort",defaultValue="_id") String sort)
    {
        return adminService.findStudentsByField(field,sort);
    }

    /**
     * Search and retrieve instructors based on a specific field and sorting order.
     *
     * @param field The field to search for.
     * @param sort  The sorting order (default: "_id").
     * @return A string representation of the found instructors.
     */
    @GetMapping("/getInstructors/{field}")
    public List<Instructor> getInstructors(@PathVariable String field, @RequestParam(value="sort",defaultValue="_id") String sort)
    {
        return adminService.findInstructorsByField(field,sort);
    }

    /**
     * Get a list of all batches.
     *
     * @return A string representation of the found batches.
     */
    @GetMapping("/batches")
    public List<Batch> getBatches()
    {
        return adminService.getBatches();
    }






//    @GetMapping("/search")
//    public ResponseEntity<List<Student>> searchStudents(
//            @RequestParam(required = false) String id,
//            @RequestParam(required = false) String name,
//            @RequestParam(required = false) String email,
//            @RequestParam(required = false) String courseId
//    ) {
//        List<Student> students = adminService.searchStudents(id, name, email, courseId);
//        return ResponseEntity.ok(students);
//    }




}

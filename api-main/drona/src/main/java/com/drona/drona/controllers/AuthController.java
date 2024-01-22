package com.drona.drona.controllers;

import com.drona.drona.models.Admin;
import com.drona.drona.models.Instructor;
import com.drona.drona.models.Student;
import com.drona.drona.models.User;
import com.drona.drona.repositories.UserRepository;
import com.drona.drona.services.RegisterationService;
import com.drona.drona.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RegisterationService registrationService;
    @Autowired
    UserService userService;


    /**
     * Endpoint to redirect to Swagger UI.
     */
    @ApiIgnore
    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }


    /**
     * Endpoint for student sign-up.
     *
     * @param student The student to sign up.
     * @return A response entity indicating the result of the sign-up operation.
     */
    @PostMapping("/StudentSignUp")
    public ResponseEntity<String> studentSignUp(@RequestBody Student student)
    {

        return userService.studentSignUp(student);

   }

    /**
     * Endpoint for instructor sign-up.
     *
     * @param instructor The instructor to sign up.
     * @return A response entity indicating the result of the sign-up operation.
     */
    @PostMapping("/InstructorSignUp")
    public ResponseEntity<String> instructorSignUp(@RequestBody Instructor instructor)
    {

        return userService.instructorSignUp(instructor);

    }

    /**
     * Endpoint for admin sign-up.
     *
     * @param admin The admin to sign up.
     * @return A response entity indicating the result of the sign-up operation.
     */
    @PostMapping("/AdminSignUp")
    public ResponseEntity<String> adminSignUp(@RequestBody Admin admin)
    {

        return userService.adminSignUp(admin);

    }


    /**
     * Endpoint for student sign-in.
     *
     * @param student The user attempting to sign in.
     * @return A response entity indicating the result of the sign-in operation.
     */
    @PostMapping("/studentsignIn")
    public ResponseEntity<String>  studentSignIn(@RequestBody Student student)
    {

        return userService.studentSignIn(student);


    }
    /**
     * Endpoint for instructor sign-in.
     *
     * @param instructor The user attempting to sign in.
     * @return A response entity indicating the result of the sign-in operation.
     */
    @PostMapping("/instructorSignIn")
    public ResponseEntity<String>  instructorSignIn(@RequestBody Instructor instructor)
    {

        return userService.instructorSignIn(instructor);


    }

    /**
     * Endpoint for admin sign-in.
     *
     * @param admin The user attempting to sign in.
     * @return A response entity indicating the result of the sign-in operation.
     */
    @PostMapping("/adminSignIn")
    public ResponseEntity<String>  instructorSignIn(@RequestBody Admin admin)
    {

        return userService.adminSignIn(admin);


    }






//    @GetMapping("/confirm")
//    public String confirm(@RequestParam("token") String token) {
//
//        return registrationService.confirmToken(token);
//    }

}

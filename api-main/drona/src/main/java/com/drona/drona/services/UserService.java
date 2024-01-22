package com.drona.drona.services;

import com.drona.drona.models.*;
import com.drona.drona.repositories.AdminRepository;
import com.drona.drona.repositories.InstructorRepository;
import com.drona.drona.repositories.StudentRepository;
import com.drona.drona.repositories.UserRepository;

import com.drona.drona.services.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
    @Autowired
    private final ConfirmationTokenService confirmationTokenService;



    /**
     * Sign up a student.
     *
     * @param student The student to sign up.
     * @return A response entity indicating the result of the sign-up operation.
     */
    public ResponseEntity<String> studentSignUp(Student student)
    {

        // Check if the student exists by email or username.
        boolean studentExists =studentRepository.findStudentByEmail(student.getEmail())
                .isPresent();
        boolean usernameExists=studentRepository.findStudentByUsername(student.getUsername())
                .isPresent();

        if(studentExists)
            return new ResponseEntity<>("User with this email already exist", HttpStatus.BAD_REQUEST);
        if(usernameExists)
            return new ResponseEntity<>("User with this name already exist", HttpStatus.BAD_REQUEST);


        // Validate email format.
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(student.getEmail());
        if(!matcher.matches())
            return new ResponseEntity<>("Wrong email format", HttpStatus.BAD_REQUEST);

        // Encrypt the password and set the user role.
        String encryptedPassword =bCryptPasswordEncoder.encode(student.getPassword());
        student.setPassword(encryptedPassword);
        student.setUserRole(UserRole.STUDENT);

        // Save the student to the repository.
        studentRepository.save(student);
        return new ResponseEntity<>("Signed Up Successfully", HttpStatus.OK);
    }


    /**
     * Sign up an instructor.
     *
     * @param instructor The instructor to sign up.
     * @return A response entity indicating the result of the sign-up operation.
     */

    public ResponseEntity<String> instructorSignUp(Instructor instructor)
    {

        // Check if the instructor exists by email or username.
        boolean instructorExists =instructorRepository.findInstructorByEmail(instructor.getEmail())
                .isPresent();
        boolean usernameExists=instructorRepository.findInstructorByUsername(instructor.getUsername())
                .isPresent();


        if(instructorExists)
            return new ResponseEntity<>("User with this email already exist", HttpStatus.BAD_REQUEST);
        if(usernameExists)
            return new ResponseEntity<>("User with this name already exist", HttpStatus.BAD_REQUEST);

        // Validate email format.
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(instructor.getEmail());
        if(!matcher.matches())
            return new ResponseEntity<>("Wrong email format", HttpStatus.BAD_REQUEST);

        // Encrypt the password and set the user role.
        String encryptedPassword =bCryptPasswordEncoder.encode(instructor.getPassword());
        instructor.setPassword(encryptedPassword);
        instructor.setUserRole(UserRole.INSTRUCTOR);

        // Save the instructor to the repository.
        instructorRepository.save(instructor);
        return new ResponseEntity<>("Signed Up Successfully", HttpStatus.OK);
    }


    /**
     * Sign up an admin.
     *
     * @param admin The admin to sign up.
     * @return A response entity indicating the result of the sign-up operation.
     */
    public ResponseEntity<String> adminSignUp(Admin admin)
    {

        // Check if the admin exists by email or username.
        boolean adminExists =adminRepository.findAdminByEmail(admin.getEmail())
                .isPresent();
        boolean usernameExists=adminRepository.findAdminByUsername(admin.getUsername())
                .isPresent();

        if(adminExists)
            return new ResponseEntity<>("User with this email already exist", HttpStatus.BAD_REQUEST);
        if(usernameExists)
            return new ResponseEntity<>("User with this name already exist", HttpStatus.BAD_REQUEST);

        // Validate email format.
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(admin.getEmail());
        if(!matcher.matches())
            return new ResponseEntity<>("Wrong email format", HttpStatus.BAD_REQUEST);

        // Encrypt the password and set the user role.
        String encryptedPassword =bCryptPasswordEncoder.encode(admin.getPassword());
        admin.setPassword(encryptedPassword);
        admin.setUserRole(UserRole.ADMIN);

        // Save the admin to the repository.
        adminRepository.save(admin);
        return new ResponseEntity<>("Signed Up Successfully", HttpStatus.OK);
    }


    /**
     * Sign in a student.
     *
     * @param student The student to sign in.
     * @return A response entity indicating the result of the sign-in operation.
     */
    public ResponseEntity<String> studentSignIn(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            Student existingStudent = studentOptional.get();
            if (bCryptPasswordEncoder.matches(student.getPassword(), existingStudent.getPassword())) {
                return new ResponseEntity<>("Student Signed In Successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Student doesn't exist", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> instructorSignIn(Instructor instructor) {
        Optional<Instructor> instructorOptional = instructorRepository.findInstructorByEmail(instructor.getEmail());
        if (instructorOptional.isPresent()) {
            Instructor existingInstructor = instructorOptional.get();
            if (bCryptPasswordEncoder.matches(instructor.getPassword(), existingInstructor.getPassword())) {
                return new ResponseEntity<>("Instructor Signed In Successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Instructor doesn't exist", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> adminSignIn(Admin admin) {
        Optional<Admin> adminOptional = adminRepository.findAdminByEmail(admin.getEmail());
        if (adminOptional.isPresent()) {
            Admin existingAdmin = adminOptional.get();
            if (bCryptPasswordEncoder.matches(admin.getPassword(), existingAdmin.getPassword())) {
                return new ResponseEntity<>("Admin Signed In Successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Admin doesn't exist", HttpStatus.NOT_FOUND);
        }
    }


}




//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//       return userRepository.findUserByEmail(email)
//               .orElseThrow(()-> new UsernameNotFoundException("User not found"));
//    }

//    public ResponseEntity<String> signUp(User user)
//    {
//
//        String regex = "^(.+)@(.+)$";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(user.getEmail());
//        if(!matcher.matches())
//            return new ResponseEntity<>("Wrong email format", HttpStatus.INTERNAL_SERVER_ERROR);
//        String encryptedPassword =bCryptPasswordEncoder.encode(user.getPassword());
//        user.setPassword(encryptedPassword);
//
//        if(user instanceof Student)
//        {
//            boolean studentExists =studentRepository.findStudentByEmail(user.getEmail())
//                    .isPresent();
//            boolean usernameExists=studentRepository.findStudentByUsername(user.getUsername())
//                    .isPresent();
//            if(studentExists)
//                return new ResponseEntity<>("User with this email already exist", HttpStatus.INTERNAL_SERVER_ERROR);
//            if(usernameExists)
//                return new ResponseEntity<>("User with this name already exist", HttpStatus.INTERNAL_SERVER_ERROR);
//            user.setUserRole(UserRole.STUDENT);
//            studentRepository.save((Student)user);
//        }
//        else if(user instanceof Instructor)
//        {
//
//            boolean instructorExists =instructorRepository.findInstructorByEmail(user.getEmail())
//                    .isPresent();
//            boolean usernameExists=instructorRepository.findInstructorByUsername(user.getUsername())
//                    .isPresent();
//            if(instructorExists)
//                return new ResponseEntity<>("User with this email already exist", HttpStatus.INTERNAL_SERVER_ERROR);
//            if(usernameExists)
//                return new ResponseEntity<>("User with this name already exist", HttpStatus.INTERNAL_SERVER_ERROR);
//
//            user.setUserRole(UserRole.INSTRUCTOR);
//            instructorRepository.save((Instructor)user);
//
//        }
//        //userRepository.save(user);
//        return new ResponseEntity<>("Signed Up Successfully", HttpStatus.OK);
//
//    }


//        boolean userExists =userRepository.findUserByEmail(user.getEmail())
//                .isPresent();
//        boolean usernameExists=userRepository.findUserByUsername(user.getUsername())
//                .isPresent();
//        if(userExists)
//            return new ResponseEntity<>("User with this email already exist", HttpStatus.INTERNAL_SERVER_ERROR);
//        if(usernameExists)
//            return new ResponseEntity<>("User with this name already exist", HttpStatus.INTERNAL_SERVER_ERROR);
//String token = UUID.randomUUID().toString();
//        //send confirmation token
//        ConfirmationToken confirmationToken= new ConfirmationToken(
//                token,
//                LocalDateTime.now(),
//                LocalDateTime.now().plusMinutes(15),newuser
//        );
//        confirmationTokenService.saveConfirmationToken(confirmationToken);
//        return token;
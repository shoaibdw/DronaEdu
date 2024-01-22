package com.drona.drona.controllers;



import com.drona.drona.models.Assignment;
import com.drona.drona.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {


    @Autowired
    private AssignmentService assignmentService;

    /**
     * Redirect to the Swagger UI when accessing the base URL.
     */
    @ApiIgnore
    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }


    /**
     * Add a new assignment to a batch.
     *
     * @param assignment The assignment to be added.
     * @param batchID    The ID of the batch to which the assignment will be added.
     * @return A response entity indicating the success of the operation.
     */
    @PostMapping("/addAssignment/{batchID}")
    public ResponseEntity<String> addAssignment(@RequestBody Assignment assignment,@PathVariable int batchID)
    {
        assignmentService.addAssignment(assignment,batchID);
        return new ResponseEntity<>(HttpStatus.OK);
    }






}

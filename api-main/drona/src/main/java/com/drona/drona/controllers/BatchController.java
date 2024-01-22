package com.drona.drona.controllers;


import com.drona.drona.models.Batch;
import com.drona.drona.services.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/instructor")
public class BatchController {

    @Autowired
    BatchService batchService;

    @ApiIgnore
    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }



    /**
     * Add a new batch to a course.
     *
     * @param batch    The batch to be added.
     * @param courseId The ID of the course to which the batch will be added.
     * @return A response entity containing the added batch information.
     */
    @PostMapping("/addBatch/{courseId}")
    public ResponseEntity<String> addBatch(@RequestBody Batch batch, @PathVariable int courseId)
    {
        batchService.addBatch(batch,courseId);
        return new ResponseEntity<>(batch.toString(),HttpStatus.OK);
    }
}

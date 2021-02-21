package com.test.async.controllers;

import java.util.List;

import com.test.async.entities.StudentDetails;
import com.test.async.services.StudentService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
public class StudentController {
    
    private StudentService studentService;

    @GetMapping(value ="/")
    public List<StudentDetails> getStudentDetials(){

        log.info("Calling student service method");
        return studentService.getStudentDetails();
    }
}

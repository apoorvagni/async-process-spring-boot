package com.test.async.services;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.validation.constraints.NotEmpty;

import com.test.async.entities.StudentDetails;
import com.test.async.externalservice.StudentExternalService;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class StudentService {

    private StudentExternalService studentExternalService;

    public List<StudentDetails> getStudentDetails() {

        CompletableFuture<List<Integer>> studentIds = studentExternalService.getStudentIds();

        CompletableFuture<List<StudentDetails>> studentDetials = studentExternalService.getStudentDetails();

        CompletableFuture.allOf(studentIds, studentDetials).join();

        List<Integer> ids = Collections.emptyList();
        List<StudentDetails> details = Collections.emptyList();
        try {
           ids = studentIds.get();
           details = studentDetials.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return details;
        }
}

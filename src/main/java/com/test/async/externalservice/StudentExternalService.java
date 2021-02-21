package com.test.async.externalservice;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.test.async.configurations.RequestTraceId;
import com.test.async.entities.StudentDetails;

import org.slf4j.MDC;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class StudentExternalService {

    private RestTemplate restTemplate;

    @Async
    public CompletableFuture<List<Integer>> getStudentIds() {
        final String url = "http://demo9128426.mockable.io/student";
        HttpHeaders commonHeader = new HttpHeaders();
        commonHeader.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        commonHeader.add(RequestTraceId.TRACE_ID, MDC.getCopyOfContextMap().get(RequestTraceId.TRACE_ID));

        log.info("calling external service to get student id's: {}", Thread.currentThread().getName());
        ResponseEntity<List<Integer>> response = restTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity<>(commonHeader), new ParameterizedTypeReference<List<Integer>>() {
                }, Collections.emptyMap());
        log.info("request - headers:---->{}",commonHeader);
        return CompletableFuture.completedFuture(response.getBody());
    }

    @Async
    public CompletableFuture<List<StudentDetails>> getStudentDetails() {

        final String url = "http://demo9128426.mockable.io/student/details";

        HttpHeaders commonHeader = new HttpHeaders();
        commonHeader.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        commonHeader.add(RequestTraceId.TRACE_ID, MDC.getCopyOfContextMap().get(RequestTraceId.TRACE_ID));

        log.info("calling external service to get student detials: {}", Thread.currentThread().getName());
        ResponseEntity<List<StudentDetails>> response = restTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity<>(commonHeader), new ParameterizedTypeReference<List<StudentDetails>>() {
                }, Collections.emptyMap());
        log.info("request - headers:---->{}",commonHeader);
        return CompletableFuture.completedFuture(response.getBody());
    }
}

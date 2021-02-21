package com.test.async.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class StudentDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private int age;
    private String email;
    
}

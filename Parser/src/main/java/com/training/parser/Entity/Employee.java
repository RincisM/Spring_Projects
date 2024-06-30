package com.training.parser.Entity;

import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// This is a Simple Java Class to represent the Employee Entity
// This is the Employee Data Structure

@Getter // Lombok Annotation to Generate Getter methods for each variable.
@Setter // Lombok Annotation to Generate Setter methods for each variable.
@NoArgsConstructor
public class Employee {
    private String id;
    private String name;
    private String department;
    private double salary;
    private String experience;
    private LocalTime workingHoursStart;
    private LocalTime workingHoursEnd;
}

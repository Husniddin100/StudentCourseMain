package com.example.students.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CourseDTO extends StudentCourseMarkDTO {
    private Integer id;
    private String name;
    private Double price;
    private String duration;
    private LocalDate createdDate;
}

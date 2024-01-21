package com.example.students.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CourseFilterDTO {
    private Integer id;
    private String name;
    private Double price;
    private String duration;
    private LocalDate createdDate;
}

package com.example.students.mapped;

import java.time.LocalDate;

public class StudentInfoMapper {
    private Integer id ;
    private String name;
    private LocalDate createdDate;

    public StudentInfoMapper(Integer id, String name, LocalDate createdDate) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
    }
}

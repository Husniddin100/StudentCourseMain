package com.example.students.mapped;

public class StudentInfoMapper {
    private Integer id ;
    private String name;
    private String level;

    public StudentInfoMapper(Integer id, String name, String level) {
        this.id = id;
        this.name = name;
        this.level = level;
    }
}

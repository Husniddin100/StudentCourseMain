package com.example.students.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Student")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "level")
    private String level;
    @Column(name = "age")
    private Integer age;
    @Column(name = "gender")
    private String gender;
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "student")
    List<StudentCourseMarkEntity> scmList;
    public StudentEntity(Integer id) {
        this.id = id;
    }

    public StudentEntity() {
    }

    public StudentEntity(Integer id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }


    public static StudentEntity id(Integer id) {
        return new StudentEntity(id);
    }
}

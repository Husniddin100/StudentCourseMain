package com.example.students.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
    @Setter
    @ToString
    @Entity
    @Table(name = "book")
    public class BookEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @Column(name = "title")
        private String title;
        @Column(name = "author")
        private String author;
        @Column(name = "publish_year")
        private LocalDateTime publishYear;
    }


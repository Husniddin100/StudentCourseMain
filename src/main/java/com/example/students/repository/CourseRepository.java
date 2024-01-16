package com.example.students.repository;

import com.example.students.entity.CourseEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository extends CrudRepository<CourseEntity, Integer> {

   List<CourseEntity>findByPriceBetween(Integer started,Integer ended);

   List<CourseEntity>findByCreatedDateBetween(LocalDateTime started,LocalDateTime ended);



}

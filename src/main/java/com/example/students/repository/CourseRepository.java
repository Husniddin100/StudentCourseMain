package com.example.students.repository;

import com.example.students.Dto.CourseFilterDTO;
import com.example.students.Dto.PaginationResultDTO;
import com.example.students.entity.CourseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository extends CrudRepository<CourseEntity, Integer> , PagingAndSortingRepository<CourseEntity,Integer> {

   List<CourseEntity>findByPriceBetween(Integer started,Integer ended);

   List<CourseEntity>findByCreatedDateBetween(LocalDateTime started,LocalDateTime ended);


}

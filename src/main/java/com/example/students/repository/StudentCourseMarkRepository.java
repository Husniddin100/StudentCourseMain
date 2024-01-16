package com.example.students.repository;

import com.example.students.entity.CourseEntity;
import com.example.students.entity.StudentCourseMarkEntity;
import com.example.students.entity.StudentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StudentCourseMarkRepository extends CrudRepository<StudentCourseMarkEntity, Integer> {
    List<StudentCourseMarkEntity> findByStudentAndCourseOrderByCreatedDateDesc(StudentEntity student, CourseEntity course);

    List<StudentCourseMarkEntity> findAllByIdAndCreatedDate(Integer id,LocalDateTime date);

    @Query("select s.mark from StudentCourseMarkEntity s where s.id=:idInput and s.createdDate=:dateInput")
    List<StudentCourseMarkEntity> findAllByIdAndCreatedDate1(@Param("idInput") Integer id, @Param("dateInput") LocalDateTime date);

    List<StudentCourseMarkEntity> findByIdAndCreatedDateBetween( Integer id, LocalDateTime startDate,LocalDateTime endDate);


}

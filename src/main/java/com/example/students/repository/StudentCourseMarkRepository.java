package com.example.students.repository;

import com.example.students.entity.CourseEntity;
import com.example.students.entity.StudentCourseMarkEntity;
import com.example.students.entity.StudentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentCourseMarkRepository extends CrudRepository<StudentCourseMarkEntity, Integer>, PagingAndSortingRepository<StudentCourseMarkEntity, Integer> {
    List<StudentCourseMarkEntity> findByStudentAndCourseOrderByCreatedDateDesc(StudentEntity student, CourseEntity course);

    @Query(" from StudentCourseMarkEntity s where s.id=:idInput and s.createdDate=:dateInput")
    List<StudentCourseMarkEntity> findAllByIdAndCreatedDate1(@Param("idInput") Integer id, @Param("dateInput") LocalDate date);

    List<StudentCourseMarkEntity> findAllByStudent_IdAndCreatedDate(Integer id, LocalDate dateTime);

    List<StudentCourseMarkEntity> findTopByStudent_IdAndCreatedDate(Integer id, LocalDateTime date);

    @Query("from StudentCourseMarkEntity s where s.student.id=:studentId order by s.createdDate desc limit 1")
    Optional<StudentCourseMarkEntity> findByStudentId(@Param("studentId") Integer id);

    @Query("from StudentCourseMarkEntity s where s.student.name=:studentId order by s.createdDate desc limit 1")
    Optional<StudentCourseMarkEntity> findByStudentName(@Param("studentId") String name);

    @Query("select s from StudentCourseMarkEntity scm inner join  scm.student s where s.name=:sName order by s.name desc limit 1")
    Optional<StudentCourseMarkEntity> findByStudentNameJoin(@Param("sName") String name);

    @Query("SELECT s.id, s.name, s.surname," +
            " c.id, c.name" +
            " from StudentCourseMarkEntity scm " +
            " inner join scm.student s " +
            " inner join scm.course c " +
            " where s.name =:sName and c.name =:cName ")
    List<Object[]> joinExample4(@Param("sName") String sName, @Param("cName") String cName);

    List<StudentCourseMarkEntity> findAllByStudent_IdAndCreatedDateBetween(Integer id, LocalDate started, LocalDate ended);
    @Query("from StudentCourseMarkEntity where student.id=?1 order by mark desc limit 1")
    Optional<StudentCourseMarkEntity> firstMark(Integer id);
}
package com.example.students.repository;

import com.example.students.Dto.StudentDTO;
import com.example.students.entity.StudentEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer>, PagingAndSortingRepository<StudentEntity, Integer> {
    @Query("from StudentEntity where age>:a and age<:b ")
    List<StudentEntity> findByAgeBetween(@Param("a") Integer a,@Param("b") Integer b);
    @Query("from StudentEntity where name=:name order by name desc limit 1")
    Optional<StudentEntity> findFirstByName(@Param("name") String name);
    @Query("from StudentEntity  where  createdDate=:created")
    List<StudentEntity> findByCreatedDate(@Param("created") LocalDateTime dateTime);
     @Query("from StudentEntity where createdDate>=:startedDateInput and createdDate<=:endedDateInput")
    List<StudentEntity> findByCreatedDateBetween(@Param("startedDateInput") LocalDateTime startDate,@Param("endedDateInput") LocalDateTime endDate);

    List<StudentEntity> findAllByName(String name, Sort sort);

    @Query("from StudentEntity where name=:nameInput")
    List<StudentEntity> findByName(@Param("nameInput") String name);

    @Query("from StudentEntity where name=:nameInput order by age desc limit 10")
    List<StudentEntity> findByNameOrderBy(@Param("nameInput") String name);

    @Query("select count(s.id) from StudentEntity s where s.name=:nameInput  order by s.age desc ")
    Long countByName(@Param("nameInput") String name);

    @Query("select s.id, s.name,s.surname from StudentEntity  s")
    List<StudentEntity> getShortInfo();

    @Query("select s.id, s.name,s.surname from StudentEntity  s")
    List<Objects[]>getshortInfo();

}

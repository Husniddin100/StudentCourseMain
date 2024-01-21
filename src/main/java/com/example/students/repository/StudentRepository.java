package com.example.students.repository;

import com.example.students.Dto.StudentDTO;
import com.example.students.entity.StudentEntity;
import com.example.students.mapped.StudentInfoMapper;
import com.example.students.mapped.StudentMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
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
    List<StudentEntity> findByAgeBetween(@Param("a") Integer a, @Param("b") Integer b);

    @Query("from StudentEntity where name=:name order by name desc limit 1")
    Optional<StudentEntity> findFirstByName(@Param("name") String name);

    @Query("from StudentEntity  where  createdDate=:created")
    List<StudentEntity> findByCreatedDate(@Param("created") LocalDateTime dateTime);

    @Query("from StudentEntity where createdDate>=:startedDateInput and createdDate<=:endedDateInput")
    List<StudentEntity> findByCreatedDateBetween(@Param("startedDateInput") LocalDateTime startDate, @Param("endedDateInput") LocalDateTime endDate);

    List<StudentEntity> findAllByName(String name, Sort sort);

    @Query("from StudentEntity where name=:nameInput")
    List<StudentEntity> findByName(@Param("nameInput") String name);

    @Query("from StudentEntity where name=:nameInput order by age desc limit 10")
    List<StudentEntity> findByNameOrderBy(@Param("nameInput") String name);

    @Query("select count(s.id) from StudentEntity s where s.name=:nameInput  order by s.age desc ")
    Long countByName(@Param("nameInput") String name);

  /*  @Query("select s.id, s.name,s.surname from StudentEntity  s")
    List<StudentEntity> getShortInfo();*/

    @Query("select new StudentEntity (s.id, s.name,s.surname) from StudentEntity  s")
    List<Objects[]> getshortInfo();

    Page<StudentEntity> findByLevel(String level, Pageable paging);

    @Query("from StudentEntity where level=?1")
    Page<StudentEntity> findByLevelQuery(String level, Pageable paging);

    Page<StudentEntity> findByGender(String gender, Pageable paging);

    @Query("select new com.example.students.mapped.StudentInfoMapper (s.id, s.name,s.surname) from StudentEntity  s")
    List<StudentInfoMapper[]> getshortInfo3();

    @Query("from StudentEntity s,BookEntity b")
    List<Objects[]> joinExample();

    @Query("from StudentEntity s,BookEntity b where s.id=b.id")
    List<Objects[]> joinExample2();

    @Query("from StudentEntity s inner join BookEntity b on s.id=b.id")
    List<Objects[]> joinExample3();

    @Query("select s from StudentEntity s inner join s.scmList scm where scm.id=:scmId")
    List<StudentEntity> joinExample(@Param("scmId") Integer scmId);

    @Query(value = "select * from student", nativeQuery = true)
    List<StudentEntity> nativeQuery();

    @Query(value = "select s.id,s.name, s.surname, " +
            " (select count(*) from student_course_mark where student_id = s.id) as markCount " +
            "from student s", nativeQuery = true)
    List<StudentMapper> joinExample12();

    @Transactional
    @Modifying
    @Query("update StudentEntity set name=:name,surname=:surname where id=:id")
    int updateStudent(@Param("name") String name,
                      @Param("surname") String surname,
                      @Param("id") Integer id);
}


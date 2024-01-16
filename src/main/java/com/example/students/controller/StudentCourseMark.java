package com.example.students.controller;

import com.example.students.Dto.StudentCourseMarkDTO;
import com.example.students.entity.StudentCourseMarkEntity;
import com.example.students.entity.StudentEntity;
import com.example.students.service.StudentMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/student_mark")
public class StudentCourseMark {
    @Autowired
    private StudentMarkService studentMarkService;

    @PostMapping("/create")
    public ResponseEntity<StudentCourseMarkDTO> create(@RequestBody StudentCourseMarkDTO dto) {
        StudentCourseMarkDTO result = studentMarkService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentCourseMarkEntity>> all(){
        return ResponseEntity.ok((List<StudentCourseMarkEntity>) studentMarkService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        StudentCourseMarkDTO dto = studentMarkService.getById(id);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/mark/{id}/{date}")
    public ResponseEntity<List<StudentCourseMarkEntity>>findByDate(@PathVariable Integer id,@PathVariable LocalDateTime date){
        List<StudentCourseMarkEntity> studentEntities=studentMarkService.getStudentGivenDate(id,date);
        return ResponseEntity.ok(studentEntities);
    }

    @GetMapping("/betweenMark/{id}/{startDate}/{endDate}")
    public ResponseEntity<List<StudentCourseMarkEntity>> getStudentsBetweenCreatedDatesMark(@PathVariable Integer id,@PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate) {
        List<StudentCourseMarkEntity> students = studentMarkService.getStudentsBetweenCreatedDatesMark(id,startDate, endDate);
        return ResponseEntity.ok(students);
    }
  /*  @GetMapping("/createdDateDesc")
    public ResponseEntity<List<StudentCourseMarkEntity>> getStudentsBetweenCreatedDatesMark(@PathVariable Integer id,@PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate) {
        List<StudentCourseMarkEntity> students = studentMarkService.getStudentsBetweenCreatedDatesMark(id,startDate, endDate);
        return ResponseEntity.ok(students);
    }*/
}

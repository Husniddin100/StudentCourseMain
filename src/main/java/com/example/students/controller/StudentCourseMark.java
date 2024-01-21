package com.example.students.controller;

import com.example.students.Dto.StudentCourseMarkDTO;
import com.example.students.Dto.StudentCourseMarkFilterDTO;
import com.example.students.Dto.StudentDTO;
import com.example.students.Dto.StudentFilterDTO;
import com.example.students.entity.StudentCourseMarkEntity;
import com.example.students.service.StudentMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/studentMark")
public class StudentCourseMark {
    @Autowired
    private StudentMarkService studentMarkService;

    @PostMapping("/create")
    public ResponseEntity<StudentCourseMarkDTO> create(@RequestBody StudentCourseMarkDTO dto) {
        StudentCourseMarkDTO result = studentMarkService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentCourseMarkDTO>> all() {
        return ResponseEntity.ok(studentMarkService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        StudentCourseMarkDTO dto = studentMarkService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/mark/{id}/{date}")
    public ResponseEntity<List<StudentCourseMarkEntity>> findByDate(@PathVariable Integer id, @PathVariable LocalDate date) {
        List<StudentCourseMarkEntity> studentEntities = studentMarkService.getStudentGivenDate(id, date);
        return ResponseEntity.ok(studentEntities);
    }

    @GetMapping("/betweenMark/{id}/{startDate}/{endDate}")
    public ResponseEntity<List<StudentCourseMarkEntity>> getStudentsBetweenCreatedDatesMark(@PathVariable Integer id, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        List<StudentCourseMarkEntity> students = studentMarkService.getStudentsBetweenCreatedDatesMark(id, startDate, endDate);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/firstMark/{id}")
    public ResponseEntity<Optional<StudentCourseMarkEntity>> firstMark(@PathVariable Integer id) {
        Optional<StudentCourseMarkEntity> courseMark = studentMarkService.firstMark(id);
        return ResponseEntity.ok(courseMark);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(studentMarkService.paginations(page, size));
    }

    @GetMapping("/getCourseInfo/{id}")
    public ResponseEntity<List<StudentCourseMarkDTO>> getCourse(@PathVariable Integer id) {
        List<StudentCourseMarkDTO> s = studentMarkService.test(id);
        return ResponseEntity.ok(s);
    }

    @PostMapping("/filter")
    public ResponseEntity<PageImpl<StudentCourseMarkDTO>> create(@RequestBody StudentCourseMarkFilterDTO  dto,
                                                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        PageImpl<StudentCourseMarkDTO> result = studentMarkService.filter(dto, page, size);
        return ResponseEntity.ok(result);
    }
}


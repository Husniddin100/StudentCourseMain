package com.example.students.controller;

import com.example.students.Dto.StudentDTO;
import com.example.students.Dto.StudentFilterDTO;
import com.example.students.entity.StudentEntity;
import com.example.students.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("")
    public ResponseEntity<StudentDTO> create(@RequestBody StudentDTO dto) {
        StudentDTO result = studentService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> all() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        Optional<StudentEntity> dto = studentService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody StudentDTO dto) {
        return ResponseEntity.ok(studentService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentService.delete(id));
    }

    @GetMapping("/givenDate/{date}")
    public ResponseEntity<List<StudentEntity>> getStudentGivenDate(@PathVariable LocalDateTime date) {
        List<StudentEntity> studentEntities = studentService.getStudentGivenDate(date);
        return ResponseEntity.ok(studentEntities);
    }

    @GetMapping("/between/{startDate}/{endDate}")
    public ResponseEntity<List<StudentEntity>> getStudentsBetweenCreatedDates(@PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate) {
        List<StudentEntity> students = studentService.getStudentsBetweenCreatedDates(startDate, endDate);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(studentService.pagination(page, size));
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<PageImpl> getStudentsByLevelWithPagination(
            @PathVariable String level,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        PageImpl students = studentService.paginationsBylevel(level, page, size);
        return ResponseEntity.ok(students);
    }
    @GetMapping("/gender/{gender}")
    public ResponseEntity<PageImpl> getStudentsByGenderWithPagination(
            @PathVariable String gender,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        PageImpl students = studentService.paginationsByGender(gender, page, size);
        return ResponseEntity.ok(students);
    }
    @PostMapping("/filter")
    public ResponseEntity<PageImpl<StudentDTO>> create(@RequestBody StudentFilterDTO dto,
                                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        PageImpl<StudentDTO> result = studentService.filter(dto, page, size);
        return ResponseEntity.ok(result);
    }


}

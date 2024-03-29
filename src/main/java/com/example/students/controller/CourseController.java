package com.example.students.controller;

import com.example.students.Dto.CourseDTO;
import com.example.students.Dto.CourseFilterDTO;
import com.example.students.Dto.StudentDTO;
import com.example.students.Dto.StudentFilterDTO;
import com.example.students.entity.CourseEntity;
import com.example.students.entity.StudentEntity;
import com.example.students.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("")
    public ResponseEntity<CourseDTO> create(@RequestBody CourseDTO dto) {
        CourseDTO result = courseService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourseDTO>> all() {
        return ResponseEntity.ok(courseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        Optional<CourseEntity> dto = courseService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody CourseDTO dto) {
        return ResponseEntity.ok(courseService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(courseService.delete(id));
    }

    @GetMapping("/between/{startedprice}/{endedprice}")
    public ResponseEntity<List<CourseEntity>> priceBetweenList(@PathVariable Integer startedprice, @PathVariable Integer endedprice) {
        List<CourseEntity> courseEntities = courseService.priceBetweenList(startedprice, endedprice);
        return ResponseEntity.ok(courseEntities);
    }

    @GetMapping("/betweenDates/{started}/{ended}")
    public ResponseEntity<List<CourseEntity>> betweenDates(@PathVariable LocalDateTime started, @PathVariable LocalDateTime ended) {
        List<CourseEntity> courseEntities = courseService.betweenDates(started, ended);
        return ResponseEntity.ok(courseEntities);
    }
    @GetMapping("/pagination")
    public ResponseEntity<PageImpl> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(courseService.paginations(page, size));
    }
    @PostMapping("/filter")
    public ResponseEntity<PageImpl<CourseDTO>> create(@RequestBody CourseFilterDTO dto,
                                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        PageImpl<CourseDTO> result = courseService.filter(dto, page, size);
        return ResponseEntity.ok(result);
    }
}

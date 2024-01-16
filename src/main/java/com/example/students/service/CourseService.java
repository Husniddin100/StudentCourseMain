package com.example.students.service;

import com.example.students.Dto.CourseDTO;
import com.example.students.Dto.StudentDTO;
import com.example.students.entity.CourseEntity;
import com.example.students.entity.StudentEntity;
import com.example.students.exp.AppBadException;
import com.example.students.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public CourseDTO create(CourseDTO dto) {
        CourseEntity entity = new CourseEntity();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());
        entity.setCreatedDate(LocalDateTime.now());
        courseRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(LocalDateTime.now());
        return dto;
    }

    public List<CourseDTO> getAll() {
        Iterable<CourseEntity> entityList = courseRepository.findAll();
        List<CourseDTO> dtoList = new LinkedList<>();
        for (CourseEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public Optional<CourseEntity> getById(Integer id) {
        Optional<CourseEntity> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new AppBadException("student not found");
        }
        return course;
    }

    public CourseDTO toDTO(CourseEntity entity) {
        CourseDTO dto = new CourseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setDuration(entity.getDuration());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public boolean update(Integer id, CourseDTO dto) {
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Student not found");
        }
        CourseEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        courseRepository.save(entity);
        return true;
    }

    public boolean delete(Integer id) {
        courseRepository.deleteById(id);
        return true;
    }

    public List<CourseEntity> priceBetweenList(Integer startedprice, Integer endedprice) {
        return courseRepository.findByPriceBetween(startedprice, endedprice);
    }

    public List<CourseEntity> betweenDates(LocalDateTime started, LocalDateTime ended) {
        return courseRepository.findByCreatedDateBetween(started, ended);
    }
}

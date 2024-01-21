package com.example.students.service;

import com.example.students.Dto.*;
import com.example.students.entity.CourseEntity;
import com.example.students.entity.StudentEntity;
import com.example.students.exp.AppBadException;
import com.example.students.repository.CourseCustomRepository;
import com.example.students.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseCustomRepository courseCustomRepository;

    public CourseDTO create(CourseDTO dto) {
        CourseEntity entity = new CourseEntity();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());
        entity.setCreatedDate(LocalDateTime.now());
        courseRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(LocalDate.from(LocalDateTime.now()));
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
        dto.setCreatedDate(LocalDate.from(entity.getCreatedDate()));
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
    public PageImpl paginations(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        Pageable paging = PageRequest.of(page - 1, size, sort);
        Page<CourseEntity> coursePage = courseRepository.findAll(paging);

        List<CourseEntity> entityList = coursePage.getContent();
        Long totalElements = coursePage.getTotalElements();

        List<CourseDTO> dtoList = new LinkedList<>();
        for (CourseEntity course : entityList) {
            dtoList.add(toDTO(course));
        }
        return new PageImpl<>(dtoList, paging, totalElements);
    }

    public PageImpl<CourseDTO> filter(CourseFilterDTO filter, int page, int size) {
        PaginationResultDTO<CourseEntity>paginationResult=courseCustomRepository.filter(filter,page,size);

        List<CourseDTO>dtoList=new LinkedList<>();
        for (CourseEntity entity:paginationResult.getList()) {
            dtoList.add(toDTO(entity));
        }
     Pageable paging=PageRequest.of(page-1,size);
        return new PageImpl<>(dtoList,paging,paginationResult.getTotalSize());
    }
}

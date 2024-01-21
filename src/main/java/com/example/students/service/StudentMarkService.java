package com.example.students.service;

import com.example.students.Dto.*;
import com.example.students.entity.CourseEntity;
import com.example.students.entity.StudentCourseMarkEntity;
import com.example.students.entity.StudentEntity;
import com.example.students.exp.AppBadException;
import com.example.students.repository.StudentCourseMarkCustomRepository;
import com.example.students.repository.StudentCourseMarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentMarkService {
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentCourseMarkRepository studentCourseMarkRepository;
    @Autowired
    private StudentCourseMarkCustomRepository studentCourseMarkCustomRepository;

    public StudentCourseMarkDTO create(StudentCourseMarkDTO dto) {
       StudentEntity student = studentService.get(dto.getStudentId());

        CourseEntity course=new CourseEntity();
        course.setId(dto.getCourseId());

        StudentCourseMarkEntity entity = new StudentCourseMarkEntity();
        entity.setStudent(student);
        entity.setCourse(course);
        entity.setMark(dto.getMark());
        entity.setCreatedDate(LocalDate.now());

        studentCourseMarkRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
    public StudentCourseMarkDTO getById(Integer id) {
        StudentCourseMarkEntity entity = get(id);

        StudentCourseMarkDTO dto = new StudentCourseMarkDTO();
        dto.setMark(entity.getMark());
        dto.setCreatedDate(entity.getCreatedDate());

        StudentEntity student = entity.getStudent();
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setSurname(student.getSurname());
        dto.setStudent(studentDTO);
        return dto;
    }

    public List<StudentCourseMarkDTO> getStudentMarkListByCourseId(Integer studentId, Integer courseId) {

        StudentEntity student = new StudentEntity(studentId);

        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(courseId);

        studentCourseMarkRepository.findByStudentAndCourseOrderByCreatedDateDesc(StudentEntity.id(studentId), courseEntity);
        return null;
    }
    public List<StudentCourseMarkDTO> getAll() {
        Iterable<StudentCourseMarkEntity> entityList = studentCourseMarkRepository.findAll();
        List<StudentCourseMarkDTO> dtoList = new LinkedList<>();
        for (StudentCourseMarkEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }


    public StudentCourseMarkEntity get(Integer id) {
        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Student not found");
        }
        return optional.get();
    }

    public List<StudentCourseMarkEntity> getStudentGivenDate(Integer id, LocalDate date) {
        return studentCourseMarkRepository.findAllByStudent_IdAndCreatedDate(id,date);
    }

    public List<StudentCourseMarkEntity> getStudentsBetweenCreatedDatesMark(Integer id, LocalDate startDate, LocalDate endDate) {
        return studentCourseMarkRepository.findAllByStudent_IdAndCreatedDateBetween(id,startDate,endDate);
    }
    public StudentCourseMarkDTO toDTO(StudentCourseMarkEntity entity) {
        StudentCourseMarkDTO dto = new StudentCourseMarkDTO();
        dto.setId(entity.getId());
        dto.setStudentId(entity.getStudent().getId());
        dto.setCourseId(entity.getCourse().getId());
        dto.setMark(entity.getMark());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Optional<StudentCourseMarkEntity> firstMark(Integer id) {
        return studentCourseMarkRepository.firstMark(id);
    }
/*    public void joinExample(Integer id){
        List<StudentMapper> mapperList = studentCourseMarkRepository.studentCourse(id);
        List<StudentDTO> dtoList = new LinkedList<>();
        for (StudentMapper mapper : mapperList) {
            StudentDTO dto = new StudentDTO();
            dto.setId(mapper.getId());
            dto.setName(mapper.getName());
            dto.setSurname(mapper.getSurname());
            dtoList.add(dto);
        }
        System.out.println();
    }*/
    public List<StudentCourseMarkDTO> test(Integer id) {
        List<Object[]> objectList = studentCourseMarkRepository.studentCourse(id);
        List<StudentCourseMarkDTO> dtoList = new LinkedList<>();
        for (Object[] obj : objectList) {
            StudentCourseMarkDTO dto = new StudentCourseMarkDTO();
            CourseDTO dto1= new CourseDTO();
            dto.setId((Integer) obj[0]);
            dto.setMark((Integer) obj[1]);
            dto.setCreatedDate((LocalDate) obj[2]);
            dto1.setId((Integer) obj[3]);
            dto1.setName((String) obj[4]);
            dto1.setDuration((String) obj[5]);
            dtoList.add(dto);
            dtoList.add(dto1);
        }
        return dtoList;
    }

    public PageImpl paginations(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        Pageable paging = PageRequest.of(page - 1, size, sort);
        Page<StudentCourseMarkEntity> studentPage = studentCourseMarkRepository.findAll(paging);

        List<StudentCourseMarkEntity> entityList = studentPage.getContent();
        Long totalElements = studentPage.getTotalElements();

        List<StudentCourseMarkDTO> dtoList = new LinkedList<>();
        for (StudentCourseMarkEntity student : entityList) {
            dtoList.add(toDTO(student));
        }
        return new PageImpl<>(dtoList, paging, totalElements);
    }

    public PageImpl<StudentCourseMarkDTO> filter(StudentCourseMarkFilterDTO filter, int page, int size) {
        PaginationResultDTO<StudentCourseMarkEntity> paginationResult=studentCourseMarkCustomRepository.filter(filter,page,size);

        List<StudentCourseMarkDTO>dtoList=new LinkedList<>();
        for (StudentCourseMarkEntity entity:paginationResult.getList()) {
            dtoList.add(toDTO(entity));
        }
        Pageable paging=PageRequest.of(page-1,size);
        return new PageImpl<>(dtoList,paging,paginationResult.getTotalSize());
    }
    }

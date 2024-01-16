package com.example.students.service;

import com.example.students.Dto.StudentCourseMarkDTO;
import com.example.students.Dto.StudentDTO;
import com.example.students.entity.CourseEntity;
import com.example.students.entity.StudentCourseMarkEntity;
import com.example.students.entity.StudentEntity;
import com.example.students.exp.AppBadException;
import com.example.students.repository.StudentCourseMarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public StudentCourseMarkDTO create(StudentCourseMarkDTO dto) {
       StudentEntity student = studentService.get(dto.getStudentId());

        CourseEntity course=new CourseEntity();
        course.setId(dto.getCourseId());

        StudentCourseMarkEntity entity = new StudentCourseMarkEntity();
        entity.setStudent(student);
        entity.setCourse(course);
        entity.setMark(dto.getMark());
        entity.setCreatedDate(LocalDateTime.now());

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
    public Iterable<StudentCourseMarkEntity> getAll() {
        Iterable<StudentCourseMarkEntity> entityList = studentCourseMarkRepository.findAll();
        return entityList;
    }


    public StudentCourseMarkEntity get(Integer id) {
        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Student not found");
        }
        return optional.get();
    }

    public List<StudentCourseMarkEntity> getStudentGivenDate(Integer id, LocalDateTime date) {
        return studentCourseMarkRepository.findAllByIdAndCreatedDate(id,date);
    }

    public List<StudentCourseMarkEntity> getStudentsBetweenCreatedDatesMark(Integer id, LocalDateTime startDate, LocalDateTime endDate) {
        return studentCourseMarkRepository.findByIdAndCreatedDateBetween(id,startDate,endDate);
    }
}

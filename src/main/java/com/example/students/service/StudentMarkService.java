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

import java.time.LocalDate;
import java.time.LocalDateTime;
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
}

package com.example.students.service;

import com.example.students.Dto.PaginationResultDTO;
import com.example.students.Dto.StudentFilterDTO;
import com.example.students.exp.AppBadException;
import com.example.students.Dto.StudentDTO;
import com.example.students.entity.StudentEntity;
import com.example.students.mapped.StudentMapper;
import com.example.students.repository.StudentCustomRepository;
import com.example.students.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
   @Autowired
   private StudentCustomRepository studentCustomRepository;
    public StudentDTO create(StudentDTO dto) {
        StudentEntity entity = new StudentEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setLevel(dto.getLevel());
        entity.setAge(dto.getAge());
        entity.setGender(dto.getGender());
        entity.setCreatedDate(LocalDateTime.now());
        studentRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(LocalDateTime.now());
        return dto;
    }

    public List<StudentDTO> getAll() {
        Iterable<StudentEntity> entityList = studentRepository.findAll();
        List<StudentDTO> dtoList = new LinkedList<>();
        for (StudentEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public Optional<StudentEntity> getById(Integer id) {
        Optional<StudentEntity> student = studentRepository.findById(id);
        return student;
    }

    public StudentDTO toDTO(StudentEntity entity) {
        StudentDTO dto = new StudentDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setAge(entity.getAge());
        dto.setLevel(entity.getLevel());
        dto.setGender(entity.getGender());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public boolean update(Integer id, StudentDTO dto) { // name, surname
        StudentEntity entity = get(id);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        studentRepository.save(entity);
        return true;
    }

    public boolean delete(Integer id) {
        studentRepository.deleteById(id);
        return true;
    }

    public StudentEntity get(Integer id) {
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Student not found");
        }
        return optional.get();
    }

    public List<StudentEntity> getStudentsBetweenCreatedDates(LocalDateTime startDate, LocalDateTime endDate) {
        return studentRepository.findByCreatedDateBetween(startDate, endDate);
    }

    public List<StudentEntity> getStudentGivenDate(LocalDateTime date) {
        return studentRepository.findByCreatedDate(date);
    }

    public PageImpl pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        Pageable paging = PageRequest.of(page - 1, size, sort);

        Page<StudentEntity> studentPage = studentRepository.findAll(paging);

        List<StudentEntity> entityList = studentPage.getContent();
        Long totalElements = studentPage.getTotalElements();

        List<StudentDTO> dtoList = new LinkedList<>();
        for (StudentEntity student : entityList) {
            dtoList.add(toDTO(student));
        }
        return new PageImpl<>(dtoList, paging, totalElements);
    }

    public PageImpl paginations(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        Pageable paging = PageRequest.of(page - 1, size, sort);
        Page<StudentEntity> studentPage = studentRepository.findAll(paging);

        List<StudentEntity> entityList = studentPage.getContent();
        Long totalElements = studentPage.getTotalElements();

        List<StudentDTO> dtoList = new LinkedList<>();
        for (StudentEntity student : entityList) {
            dtoList.add(toDTO(student));
        }
        return new PageImpl<>(dtoList, paging, totalElements);
    }

    public List<StudentEntity> getAllByNameWithSort() {
        studentRepository.findAllByName("All", Sort.by(Sort.Direction.DESC, "createdDate"));
        studentRepository.findAll(Sort.by(Sort.Direction.DESC, "surname"));
        return null;
    }

    public PageImpl paginationsBylevel(String level, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        Pageable paging = PageRequest.of(page - 1, size, sort);
        Page<StudentEntity> studentPage = studentRepository.findByLevel(level, paging);

        List<StudentEntity> entityList = studentPage.getContent();
        Long totalElements = studentPage.getTotalElements();

        List<StudentDTO> dtoList = new LinkedList<>();
        for (StudentEntity student : entityList) {
            dtoList.add(toDTO(student));
        }
        return new PageImpl<>(dtoList, paging, totalElements);
    }


    public PageImpl paginationsByGender(String gender, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate  ");

        Pageable paging = PageRequest.of(page - 1, size, sort);
        Page<StudentEntity> studentPage = studentRepository.findByGender(gender, paging);

        List<StudentEntity> entityList = studentPage.getContent();
        Long totalElements = studentPage.getTotalElements();

        List<StudentDTO> dtoList = new LinkedList<>();
        for (StudentEntity student : entityList) {
            dtoList.add(toDTO(student));
        }
        return new PageImpl<>(dtoList, paging, totalElements);
    }

    public void test() {
        List<Objects[]> objectList = studentRepository.getshortInfo();
        List<StudentDTO> dtoList = new LinkedList<>();
        for (Object[] obj : objectList) {
            StudentDTO dto = new StudentDTO();
            dto.setId((Integer) obj[0]);
            dto.setName((String) obj[1]);
            dto.setSurname((String) obj[2]);
            dtoList.add(dto);
        }
    }
   public void joinExample(){
       List<StudentMapper> mapperList = studentRepository.joinExample12();
       List<StudentDTO> dtoList = new LinkedList<>();
       for (StudentMapper mapper : mapperList) {
           StudentDTO dto = new StudentDTO();
           dto.setId(mapper.getId());
           dto.setName(mapper.getName());
           dto.setSurname(mapper.getSurname());
           dtoList.add(dto);
       }
       System.out.println();
   }

   public boolean updates(Integer id ,StudentDTO dto){
        int effectiveRows=studentRepository.updateStudent(dto.getName(),dto.getSurname(),id);
        return true;
   }
    public PageImpl<StudentDTO> filter(StudentFilterDTO filter, int page, int size) {
        PaginationResultDTO<StudentEntity> paginationResult = studentCustomRepository.filter(filter, page, size);

        List<StudentDTO> dtoList = new LinkedList<>();
        for (StudentEntity entity : paginationResult.getList()) {
            dtoList.add(toDTO(entity));
        }

        Pageable paging = PageRequest.of(page - 1, size);
        return new PageImpl<>(dtoList, paging, paginationResult.getTotalSize());
    }
}
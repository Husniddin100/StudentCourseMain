package com.example.students.repository;

import com.example.students.Dto.CourseFilterDTO;
import com.example.students.Dto.PaginationResultDTO;
import com.example.students.Dto.StudentCourseMarkFilterDTO;
import com.example.students.entity.CourseEntity;
import com.example.students.entity.StudentCourseMarkEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentCourseMarkCustomRepository {
    @Autowired
    private EntityManager entityManager;
    public PaginationResultDTO<StudentCourseMarkEntity> filter(StudentCourseMarkFilterDTO filter, int page, int size) {
        StringBuilder builder=new StringBuilder();
        Map<String,Object> params=new HashMap<>();
        if (filter.getId()!=null){
            builder.append(" and id=:id");
            params.put("id",filter.getId());
        }
        if (filter.getStudentId()!=null){
            builder.append(" and studentId=:studentId ");
            params.put("studentId",filter.getStudentId());
        }
        if (filter.getCourseId()!=null) {
            builder.append(" and courseId=:courseId ");
            params.put("courseId", filter.getCourseId());
        }
        if (filter.getMark()!=null){
            builder.append(" and mark=:mark ");
            params.put("mark",filter.getMark());
        }
        if (filter.getCreatedDate()!=null){
            builder.append(" and createdDate=:createdDate ");
            params.put("createdDate",filter.getCreatedDate());
        }

        StringBuilder stringBuilder=new StringBuilder("from student_course_mark where 1=1 " );
        stringBuilder.append(builder);
        stringBuilder.append(" order by createdDate desc ");

        StringBuilder countBuilder=new StringBuilder(" select count(s) from student_course_mark s where 1=1 ");
        countBuilder.append(builder);

        Query selectQuery=entityManager.createQuery(stringBuilder.toString());
        selectQuery.setMaxResults(size);
        selectQuery.setFirstResult((page-1)*size);
        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String,Object>param:params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }


        List<StudentCourseMarkEntity> entityList=selectQuery.getResultList();
        Long totalElements= (Long) countQuery.getSingleResult();

        return new PaginationResultDTO<StudentCourseMarkEntity>(totalElements,entityList);
    }

}

package com.example.students.repository;

import com.example.students.Dto.CourseFilterDTO;
import com.example.students.Dto.PaginationResultDTO;
import com.example.students.Dto.StudentFilterDTO;
import com.example.students.entity.CourseEntity;
import com.example.students.entity.StudentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class CourseCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public PaginationResultDTO<CourseEntity> filter(CourseFilterDTO filter, int page, int size) {
        StringBuilder builder=new StringBuilder();
        Map<String,Object>params=new HashMap<>();
        if (filter.getId()!=null){
            builder.append(" and id=:id");
            params.put("id",filter.getId());
        }
        if (filter.getName()!=null){
            builder.append(" and lower(name) like :name ");
            params.put("name","%"+filter.getName().toLowerCase()+"%");
        }
        if (filter.getPrice()!=null) {
            builder.append(" and price=:price ");
            params.put("price", filter.getPrice());
        }
        if (filter.getDuration()!=null){
            builder.append(" and duration=:duration ");
            params.put("duration",filter.getDuration());
        }
        if (filter.getCreatedDate()!=null){
            builder.append(" and createdDate=:createdDate ");
            params.put("createdDate",filter.getCreatedDate());
        }

        StringBuilder stringBuilder=new StringBuilder("from CourseEntity where 1=1 " );
        stringBuilder.append(builder);
        stringBuilder.append(" order by createdDate desc ");

        StringBuilder countBuilder=new StringBuilder(" select count(s) from CourseEntity s where 1=1 ");
        countBuilder.append(builder);

        Query selectQuery=entityManager.createQuery(stringBuilder.toString());
        selectQuery.setMaxResults(size);
        selectQuery.setFirstResult((page-1)*size);
        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String,Object>param:params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }


        List<CourseEntity> entityList=selectQuery.getResultList();
        Long totalElements= (Long) countQuery.getSingleResult();

        return new PaginationResultDTO<CourseEntity>(totalElements,entityList);
        }
    }

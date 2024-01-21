package com.example.students.repository;

import com.example.students.Dto.PaginationResultDTO;
import com.example.students.Dto.StudentFilterDTO;
import com.example.students.entity.StudentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Repository
public class StudentCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public PaginationResultDTO<StudentEntity> filter(StudentFilterDTO filter, int page, int size) {
        StringBuilder builder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (filter.getId() != null) {
            builder.append("and id =:id ");
            params.put("id", filter.getId());
        }
        if (filter.getName() != null) {
            builder.append("and name =:name ");
            params.put("name", filter.getName());
        }
        if (filter.getSurname() != null) {
            builder.append("and lower(surname) like :surname ");
            params.put("surname", "%" + filter.getSurname().toLowerCase() + "%");
        }
        if (filter.getLevel()!=null){
            builder.append(" and level=:level ");
            params.put("level",filter.getName());
        }
        if (filter.getAge()!=null){
            builder.append(" and age=:age ");
            params.put("age",filter.getAge());
        }
        if (filter.getGender()!=null){
            builder.append(" and gender=:gender ");
            params.put("gender",filter.getGender());
        }
        if (filter.getFromDate() != null && filter.getToDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getToDate(), LocalTime.MAX);
            builder.append("and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getFromDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MAX);
            builder.append("and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getToDate() != null) {
            LocalDateTime toDate = LocalDateTime.of(filter.getToDate(), LocalTime.MAX);
            builder.append("and createdDate <= :toDate ");
            params.put("toDate", toDate);
        }

        StringBuilder selectBuilder = new StringBuilder("FROM StudentEntity s where 1=1 ");
        selectBuilder.append(builder);
        selectBuilder.append(" order by createdDate desc ");

        StringBuilder countBuilder = new StringBuilder("Select count(s) FROM StudentEntity s where 1=1 ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(size);
        selectQuery.setFirstResult((page-1)*size);
        Query countQuery = entityManager.createQuery(countBuilder.toString());


       /* if (filter.getId() != null) {
            query.setParameter("id", filter.getId());
        }
        if (filter.getName() != null) {
            query.setParameter("name", filter.getName());
        }
        if(filter.getSurname() != null){
            query.setParameter("surname", filter.getSurname());
        }*/
        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }
        List<StudentEntity> entityList = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();

        return new PaginationResultDTO<StudentEntity>(totalElements,entityList);
    }

}



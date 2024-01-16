package com.example.students.Dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PaginationResultDTO<T> {
    private Long totalSize;
    private List<T>list;
}

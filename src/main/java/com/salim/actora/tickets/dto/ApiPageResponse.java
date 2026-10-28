package com.salim.actora.tickets.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiPageResponse<T> {

    private List<T> content;
    private int pageNo;
    private int pageSize;
    private int pageElement;
    private int totalPages;
}

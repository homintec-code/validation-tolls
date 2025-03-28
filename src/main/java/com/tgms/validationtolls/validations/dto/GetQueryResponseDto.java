package com.tgms.validationtolls.validations.dto;

import com.tgms.validationtolls.validations.model.Validation;
import lombok.Data;

import java.util.List;

@Data
public class GetQueryResponseDto {
    private List<Validation> data;
    private int dataCount;
    private int totalRecordsCount;
    private List<Object> pageNumbers;

    // Getters and Setters
}
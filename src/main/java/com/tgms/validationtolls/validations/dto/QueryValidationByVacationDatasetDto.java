package com.tgms.validationtolls.validations.dto;

import lombok.Data;

@Data
public class QueryValidationByVacationDatasetDto {
    private int requestedPageNumber;
    private int dataLength = 20;
    private Long vacation_id = null;
    private int isExo;

    // Getters and Setters
}
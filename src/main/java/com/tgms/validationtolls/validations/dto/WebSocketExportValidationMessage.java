package com.tgms.validationtolls.validations.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WebSocketExportValidationMessage {

    private int requestedPageNumber;
    private int dataLength = 20;
    private String searchString = "";
    private Long vacation_id;
    private LocalDate date_start;
    private LocalDate date_end;
    private int isExo;
}

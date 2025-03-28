package com.tgms.validationtolls.validations.dto;

import lombok.Data;

@Data
public class WebSocketExportValidationByVacationMessage {

    private int requestedPageNumber;
    private int dataLength = 20;
    private Long vacation_id;
    private int isExo;
}

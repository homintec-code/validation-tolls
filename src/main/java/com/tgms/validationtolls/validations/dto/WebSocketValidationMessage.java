package com.tgms.validationtolls.validations.dto;

import lombok.Data;

@Data
public class WebSocketValidationMessage {

    private String type;
    private Long siteId;
    private Long voieId;

}

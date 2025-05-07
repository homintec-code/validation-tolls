package com.tgms.validationtolls.validations.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketContentData {

    @JsonProperty("id")
    private String id;
    private String eventName;
    private String customerName;
    private LocalDateTime date;

}

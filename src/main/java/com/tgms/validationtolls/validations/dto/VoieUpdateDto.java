package com.tgms.validationtolls.validations.dto;

import lombok.Data;

@Data
public class VoieUpdateDto {
    private String nom;
    private String ip;
    private String sens;
    private Long site_id;
}

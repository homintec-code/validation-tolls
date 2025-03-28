package com.tgms.validationtolls.validations.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginWayDto {
    private Long percepteurId;
    private Long voieId;

    public LoginWayDto(Long percepteurId, Long voieId) {

        this.percepteurId = percepteurId;
        this.voieId = voieId;
    }
}

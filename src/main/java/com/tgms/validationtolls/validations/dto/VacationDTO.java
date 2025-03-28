package com.tgms.validationtolls.validations.dto;

public class VacationDTO {
    private Long id;
    private String dateStart;
    private String dateEnd;

    public VacationDTO(Long id, String dateStart, String dateEnd) {
        this.id = id;
        this.dateStart = String.valueOf(dateStart);
        this.dateEnd = dateEnd;
    }

    // Getters and Setters
}

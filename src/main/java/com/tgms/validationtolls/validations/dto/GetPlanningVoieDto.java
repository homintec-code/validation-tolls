package com.tgms.validationtolls.validations.dto;


import lombok.Data;

@Data
public class GetPlanningVoieDto {

    private String type_vacation_id;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}

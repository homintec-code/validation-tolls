package com.tgms.validationtolls.validations.dto;


import java.time.LocalTime;

public class CreateTypeVacationDto {

    private LocalTime start_time;

    private LocalTime end_time;

    private String label;


    public void setEnd_time(String end_time) {
        this.end_time = LocalTime.parse(end_time);
    }
    public LocalTime getEnd_time() {
        return end_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = LocalTime.parse(start_time);
    }
    public LocalTime getStart_time() {
        return start_time;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}

package com.tgms.validationtolls.validations.dto;

import java.util.List;

public class ValidationResponse {
    private String type;
    private Long siteId;
    private Long voieId;
    private List<Object> loggers;

    // Constructor, getters, and setters
    public ValidationResponse(String type, Long siteId, Long voieId, List<Object> loggers) {
        this.type = type;
        this.siteId = siteId;
        this.voieId = voieId;
        this.loggers = loggers;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getVoieId() {
        return voieId;
    }

    public void setVoieId(Long voieId) {
        this.voieId = voieId;
    }

    public List<Object> getLoggers() {
        return loggers;
    }

    public void setLoggers(List<Object> loggers) {
        this.loggers = loggers;
    }
}


package com.tgms.validationtolls.validations.dto;

public class PostDataByCabineDto {
    private String type;
    private Long siteId;

    // Getters and setters
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
}
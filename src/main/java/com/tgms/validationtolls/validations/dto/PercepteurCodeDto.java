package com.tgms.validationtolls.validations.dto;

public class PercepteurCodeDto {

  public Long code;
  public Long percepteur_id;

    public void setCode(String code) {
        this.code = Long.valueOf(code);
    }
    public void setPercepteur_id(Long percepteur_id) {
        this.percepteur_id = percepteur_id;
    }
    public Long getCode() {
        return code;
    }
    public Long getPercepteur_id() {
        return percepteur_id;
    }
}

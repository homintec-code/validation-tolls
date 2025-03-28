package com.tgms.validationtolls.validations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tgms.validationtolls.Audit;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "percepteurs")
@Data
public class Percepteur extends Audit {

    private String nom;

    private  String prenom;

    @Column(name = "code_percepteur")
    private  Integer codePercepteur;

}

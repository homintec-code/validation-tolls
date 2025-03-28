package com.tgms.validationtolls.validations.service;

import com.tgms.validationtolls.utils.UtilHelper;
import com.tgms.validationtolls.validations.dto.*;
import com.tgms.validationtolls.validations.model.Percepteur;
import com.tgms.validationtolls.validations.model.Sites;
import com.tgms.validationtolls.validations.repository.PercepteurProjection;
import com.tgms.validationtolls.validations.repository.PercepteurRepository;
import com.tgms.validationtolls.validations.repository.SiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class PercepteurService {

    private static final Logger log = LoggerFactory.getLogger(PercepteurService.class);

    public final PercepteurRepository percepteurRepository;
    public final SiteRepository siteRepository;


    @Autowired
    public PercepteurService(PercepteurRepository percepteurRepository, SiteRepository siteRepository) {
        this.percepteurRepository = percepteurRepository;
        this.siteRepository = siteRepository;
    }

    public Percepteur createPercepteur(SavePercepteur percepteur) {
        Optional<Sites> site = siteRepository.findById(Long.valueOf(1));
        Percepteur newPercepteur = new Percepteur();
        newPercepteur.setNom(percepteur.getNom());
        newPercepteur.setPrenom(percepteur.getPrenom());
        int code  = Integer.parseInt(UtilHelper.generateDigits(4));
        newPercepteur.setCodePercepteur(code);
        return percepteurRepository.save(newPercepteur);
    }


    public  List<PercepteurProjection>getPercepteursOrderByNom() {
        Sort sort = Sort.by(Sort.Direction.DESC, "nom");
        List<PercepteurProjection>percepteurs = percepteurRepository.findAllProjectedBy(sort);
        return percepteurs;
    }


    public Percepteur findOnes(Long id) {
        return percepteurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Percepteur not found"));
    }



    public boolean remove(Long id) {
        percepteurRepository.deleteById(id);
        return percepteurRepository.existsById(id);
    }





    private String generateDigits(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // Generate a random digit between 0-9
        }
        return sb.toString();
    }





    public Percepteur findOne(Long percepteurId) {

        return  percepteurRepository.findById(percepteurId).orElse(null);
    }



}

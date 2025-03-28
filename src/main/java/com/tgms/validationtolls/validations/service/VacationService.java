package com.tgms.validationtolls.validations.service;

import com.tgms.validationtolls.validations.dto.CreatePlaningCodeDto;
import com.tgms.validationtolls.validations.dto.VacationDTO;
import com.tgms.validationtolls.validations.enums.VacationStatus;
import com.tgms.validationtolls.validations.model.Percepteur;
import com.tgms.validationtolls.validations.model.TypeVacation;
import com.tgms.validationtolls.validations.model.Vacation;
import com.tgms.validationtolls.validations.model.Voie;
import com.tgms.validationtolls.validations.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class VacationService {

    public final VacationRepository vacationRepository;

    public final PercepteurRepository percepteurRepository;

    public final VoieRepository voieRepository;

    public final TypeVacationRepository typeVacationRepository;


    @Autowired
    public VacationService (VacationRepository vacationRepository, PercepteurRepository percepteurRepository, VoieRepository voieRepository,TypeVacationRepository typeVacationRepository) {
        this.vacationRepository = vacationRepository;
        this.percepteurRepository = percepteurRepository;
        this.voieRepository = voieRepository;
        this.typeVacationRepository = typeVacationRepository;


    }


    // Create a new vacation
    public Vacation createVacation(Vacation vacation) {
        return null;
    }

    // Find all vacations
    public List<Vacation> findAllVacations() {
        return vacationRepository.findAll();
    }

    public Vacation updateByPercepteur(Long vacationId) {
        try {
            // Find the vacation by its ID
            Vacation vacation = vacationRepository.findById(vacationId).orElse(null);

            if (vacation == null) {
                return null; // Vacation not found, return null
            }

            // Update the vacation fields
            vacation.setDateEnd(String.valueOf(LocalDateTime.now()));  // Set current date and time as date_end
            vacation.setLogout(true);  // Set isLogout to true

            // Save the updated vacation
            return vacationRepository.save(vacation); // Save and return the updated vacation
        } catch (Exception e) {
            // Handle exceptions (optional logging can be added)
            return null;
        }
    }

    public List<Vacation> getAllVacationPercepteurIsNotClosingd() {
        try {
            return vacationRepository.findAllByIsCloseFalseOrderByIdDesc();
        } catch (Exception e) {
            return null;
        }
    }

    public List<VacationDTO> getVacationByPercepteur(Long percepteurId) {
        List<VacationProjection> vacations = vacationRepository.findByPercepteurId(percepteurId);
        List<VacationDTO> vacationDTOs = new ArrayList<>();
        for (VacationProjection vacation : vacations) {
            VacationDTO dto = new VacationDTO(vacation.getId(), vacation.getDateStart(), vacation.getDateEnd());
            vacationDTOs.add(dto);
        }
        return vacationDTOs;
    }
    public Vacation findOne(Long vacationId) {

        return vacationRepository.findById(vacationId).orElse(null);
    }

    public Vacation findVacationById(Long vacationId) {
        return vacationRepository.findById(vacationId).orElse(null);
    }


    public Vacation closeVacationByPercepteurs(Long percepteurId) {
        try {
            Vacation vacation = vacationRepository.findFirstByPercepteurIdAndIsCloseFalseOrderByIdDesc(percepteurId);
            if (vacation != null) {
                vacation.setClose(true);
                vacation.setStatut(VacationStatus.valueOf("finished"));
                return vacationRepository.save(vacation);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Vacation not found or error occurred.", e);
        }
    }


    public Vacation createPlanningVacation(CreatePlaningCodeDto createPlaningCodeDto) throws BadRequestException {
        try {
            System.out.print("body conntrol: "+ createPlaningCodeDto.getType_vacation_id());
            Percepteur percepteur = percepteurRepository.findById(Long.valueOf(createPlaningCodeDto.getPercepteur_id())).orElse(null);

            Voie voie = voieRepository.findById(Long.valueOf(createPlaningCodeDto.getVoie_id())).orElse(null);


            TypeVacation typeVacation = typeVacationRepository.findById(Long.valueOf(createPlaningCodeDto.getType_vacation_id())).orElse(null);

            if (percepteur == null || voie == null || typeVacation == null) {
                throw new BadRequestException("Missing data");
            }

            Vacation planning = new Vacation();
            planning.setPercepteur(percepteur);
            planning.setVoie(voie);
            planning.setClose(false);
            planning.setLogout(false);
            planning.setStatut(VacationStatus.pending);
            planning.setDate(createPlaningCodeDto.getDate());
            planning.setTypeVacation(typeVacation);

            return vacationRepository.save(planning);
        } catch (Exception e) {
            throw new BadRequestException("Error creating vacation", e);
        }
    }


    private boolean isCurrentVacation(TypeVacation vacation, String currentTime) {
        LocalTime start = vacation.getStartTime();
        LocalTime end = vacation.getEndTime();
        LocalTime now = LocalTime.parse(currentTime);

        return (now.isAfter(start) && now.isBefore(end)) || (end.isBefore(start) && (now.isAfter(start) || now.isBefore(end)));
    }

    public List<Vacation> getPlanningByVoie(Long voieId) {
        return (List<Vacation>) vacationRepository.findByVoieId(voieId);
    }





    public Vacation findVacationAllByCabineIdWithIsLogoutEnd(Long voieId) {

        return vacationRepository.findByVoieIdAndIsLogoutTrue(voieId);

    }


}



package com.tgms.validationtolls.validations.service;

import com.tgms.validationtolls.validations.dto.CreateTypeVacationDto;
import com.tgms.validationtolls.validations.model.TypeVacation;
import com.tgms.validationtolls.validations.repository.TypeVacationRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeVacationService {


    private final TypeVacationRepository typeVacationRepository;


    @Autowired
    public TypeVacationService(TypeVacationRepository typeVacationRepository) {
        this.typeVacationRepository = typeVacationRepository;
    }

    public TypeVacation createTypeVacation(CreateTypeVacationDto createTypeVacationDto) throws BadRequestException {
        try {
            TypeVacation newTypeVacation = new TypeVacation();
            newTypeVacation.setStartTime(createTypeVacationDto.getStart_time());
            newTypeVacation.setEndTime(createTypeVacationDto.getEnd_time());
            newTypeVacation.setLabel(createTypeVacationDto.getLabel());

            return typeVacationRepository.save(newTypeVacation);
        } catch (Exception e) {
            throw new BadRequestException("Invalid data received", e);
        }
    }

    public TypeVacation updateTypeVacation(Long type_vacation_id, CreateTypeVacationDto createTypeVacationDto) throws BadRequestException {
        try {

            TypeVacation typeVacation = typeVacationRepository.findById(type_vacation_id)
                    .orElseThrow(() -> new RuntimeException("Vacation not found for id: " + type_vacation_id));

            typeVacation.setEndTime(createTypeVacationDto.getEnd_time());
            typeVacation.setStartTime(createTypeVacationDto.getStart_time());
            typeVacation.setLabel(createTypeVacationDto.getLabel());

            return typeVacationRepository.save(typeVacation);
        } catch (Exception e) {
            throw new BadRequestException("Invalid data received", e);
        }
    }

    public List<TypeVacation> getTypeVacation() {
        return typeVacationRepository.findAll();
    }



}

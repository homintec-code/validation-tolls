package com.tgms.validationtolls.validations.controllers;

import com.tgms.validationtolls.validations.dto.GetQueryResponseDto;
import com.tgms.validationtolls.validations.dto.QueryValidationByPercepteurByVacationDatasetDto;
import com.tgms.validationtolls.validations.service.ValidationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private ValidationsService validationService;

}
package com.tgms.validationtolls.validations.controllers;

import com.tgms.validationtolls.validations.dto.TicketContentData;
import com.tgms.validationtolls.validations.dto.TicketData;
import com.tgms.validationtolls.validations.service.TicketPrintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketPrintController {

    private final TicketPrintService ticketPrintService;

    public TicketPrintController(TicketPrintService ticketPrintService) {
        this.ticketPrintService = ticketPrintService;
    }

    @PostMapping("/print")
    public ResponseEntity<String> printTicket(@RequestBody TicketContentData ticketData) {
        try {
            ticketPrintService.generateAndPrintTicket(ticketData);
            return ResponseEntity.ok("Ticket printed successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Failed to print ticket: " + e.getMessage());
        }
    }
}
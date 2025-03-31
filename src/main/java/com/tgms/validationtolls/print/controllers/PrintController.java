package com.tgms.validationtolls.print.controllers;

import com.tgms.validationtolls.print.service.DocumentPrintService;
import com.tgms.validationtolls.print.service.HtmlToPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;

@RestController
@RequestMapping("/api/v1/print")
public class PrintController {

    @Autowired
    private HtmlToPdfService htmlToPdfService;

    @Autowired
    private DocumentPrintService pdfPrintService;

    @PostMapping(value = "/print-html")
    public String printHtml(@RequestBody String htmlContent) {
        try {
            // Convertir HTML en XHTML propre
            String xhtml = HtmlToPdfService.cleanHtml(htmlContent);

            // Générer le PDF
            byte[] pdf = HtmlToPdfService.generatePdf(xhtml);

            // Sauvegarder le PDF (pour test)
            try (FileOutputStream fos = new FileOutputStream("output.pdf")) {
                fos.write(pdf);
            }
            HtmlToPdfService.printPdf(pdf);

            return "PDF généré avec succès!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur: " + e.getMessage();
        }
    }


   @GetMapping("text-print")
    public  String prints(){
       String message = "Bonjour, ceci est un test d'impression avec Spring Boot!";

       DocumentPrintService.printText(message);
       return "Message envoyé à l'imprimante.";

   }
}

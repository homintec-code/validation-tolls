package com.tgms.validationtolls.print.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.springframework.stereotype.Service;

import javax.print.*;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@Service
public class DocumentPrintService {

    public void printDocument(String filePath) throws IOException {
        PDDocument document = PDDocument.load(new File(filePath));

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));

        // Find the default printer
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
        if (defaultPrintService != null) {
            try {
                job.setPrintService(defaultPrintService);
                job.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No default printer found.");
        }

        document.close();
    }


    public static void printText(String content) {
        try {
            // Configuration du contenu à imprimer
            ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());

            // Trouver l'imprimante par défaut
            PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
            if (printService == null) {
                System.out.println("Aucune imprimante trouvée !");
                return;
            }

            // Créer un Doc à imprimer
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc doc = new SimpleDoc(inputStream, flavor, null);

            // Créer un objet de travail d'impression
            DocPrintJob printJob = printService.createPrintJob();

            // Lancer l'impression
            printJob.print(doc, null);
            System.out.println("Impression envoyée à l'imprimante.");
        } catch (PrintException e) {
            e.printStackTrace();
        }
    }



}
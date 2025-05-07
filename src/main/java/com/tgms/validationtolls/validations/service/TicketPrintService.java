package com.tgms.validationtolls.validations.service;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.image.*;
import com.tgms.validationtolls.validations.dto.TicketContentData;
import com.tgms.validationtolls.validations.dto.TicketData;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.printing.PDFPageable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;

@Service
public class TicketPrintService {

    @Value("classpath:static/logo.png")
    private Resource logoFile;

    public void printTicket(TicketData ticketData) throws IOException {
        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            EscPos escpos = new EscPos(outputStream);

            // Initialize printer
            escpos.initializePrinter();

            // Print logo using proper image wrapper
            printLogo(escpos);

            // Print ticket content
            escpos
                    .writeLF(ticketData.getTitle())
                    .writeLF("Date: " + ticketData.getDate())
                    .feed(3)
                    .cut(EscPos.CutMode.PART);

            // Send to printer
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(outputStream.toByteArray(), flavor, null);
           // printService.createPrintJob().print(doc, null);

        }
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
    private void printLogo(EscPos escpos) throws IOException {
        BufferedImage logoImage = ImageIO.read(logoFile.getInputStream());

        // Convert image to ESC/POS format
        CoffeeImage coffeeImage = new CoffeeImageImpl(logoImage);
        BitImageWrapper imageWrapper = new BitImageWrapper()
                .setJustification(EscPos.Justification.Center)
                .setMode(BitImageWrapper.BitImageMode._8DotSingleDensity);

        escpos.write((ImageWrapperInterface) imageWrapper, (EscPosImage) coffeeImage);
    }

        public void generateAndPrintTicket(TicketContentData ticket) throws IOException {
            // Create a new PDF document
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                // Write ticket content to PDF
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.COURIER, 12);
                    contentStream.newLineAtOffset(50, 700);
                    contentStream.showText("TICKET ID: " + ticket.getId());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("EVENT: " + ticket.getEventName());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("CUSTOMER: " + ticket.getCustomerName());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("DATE: " + ticket.getDate().toString());
                    contentStream.endText();
                }

                // Save PDF temporarily (optional)
                String filePath = "ticket.pdf";
                document.save(filePath);

                // Send PDF to printer
                System.out.print("Printing: " + filePath);

                printPDF(filePath);

            } catch ( PrintException e) {
                System.out.print("Printing failed: " + e.getMessage());

                throw new RuntimeException(e);
            }
        }

        // Print the PDF
        private void printPDFs(String filePath) throws IOException {
            // Use OS-specific command to print
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // Windows
                Runtime.getRuntime().exec("notepad /p " + filePath);
            } else if (os.contains("nix") || os.contains("mac")) {
                // Linux/macOS
                Runtime.getRuntime().exec("lp " + filePath);
            }
    }

    private void printPDF(String filePath) throws IOException, PrintException {
        // Open the PDF file
        try (FileInputStream fis = new FileInputStream(filePath)) {
            // Create a PDF document "flavor"
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;

            // Find the default printer
            PrintService defaultPrinter = PrintServiceLookup.lookupDefaultPrintService();
            if (defaultPrinter == null) {
                throw new PrintException("No default printer found");
            }

            // Configure print job attributes (e.g., number of copies)
            PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
            attributes.add(new Copies(1)); // Print 1 copy

            // Create a print job
            DocPrintJob job = defaultPrinter.createPrintJob();
            Doc doc = new SimpleDoc(fis, flavor, null);

            // Print the document
            job.print(doc, attributes);
        }

    }


    private void printPDFg(String filePath) throws IOException, PrinterException {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));

            // Show print dialog (optional)
            if (job.printDialog()) {
                job.print();
            }
        }
    }
}
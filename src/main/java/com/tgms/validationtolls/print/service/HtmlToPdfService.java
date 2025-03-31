package com.tgms.validationtolls.print.service;

import org.springframework.stereotype.Service;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.print.*;
import java.io.*;

@Service
public class HtmlToPdfService {

    public void convertHtmlToPdf(String htmlContent, String outputPdfPath) throws Exception {
        try (OutputStream outputStream = new FileOutputStream(outputPdfPath)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
        }
    }


    public String convertToXhtml(String html) {
        Tidy tidy = new Tidy();
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setXHTML(true);
        tidy.setShowWarnings(false);
        tidy.setQuiet(true);
        tidy.setMakeClean(true);
        tidy.setFixComments(true);
        tidy.setFixBackslash(true);
        tidy.setBreakBeforeBR(true);

        StringWriter writer = new StringWriter();
        tidy.parse(new StringReader(html), writer);

        return writer.toString();
    }


    public static String cleanHtml(String html) {
        Tidy tidy = new Tidy();
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setXHTML(true);
        tidy.setQuiet(true);
        tidy.setShowWarnings(false);

        StringWriter writer = new StringWriter();
        tidy.parse(new StringReader(html), writer);
        return writer.toString();
    }

    public static byte[] generatePdf(String xhtml) throws Exception {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(xhtml);
        renderer.layout();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        renderer.createPDF(outputStream);
        return outputStream.toByteArray();
    }

    // Ajoutez cette méthode dans PrintController
    public static void printPdf(byte[] pdf) throws Exception {
        PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
        DocPrintJob job = ps.createPrintJob();
        Doc doc = new SimpleDoc(pdf, DocFlavor.BYTE_ARRAY.PDF, null);
        job.print(doc, null);
    }
}
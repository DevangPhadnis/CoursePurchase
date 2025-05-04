package com.example.CoursePurchase.serviceImpl;

import com.example.CoursePurchase.service.PDFGeneratorService;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class PDFGeneratorServiceImpl implements PDFGeneratorService {

    @Override
    public byte[] generatePDF(Map<String, String> paymentDetailsMap) {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteArrayOutputStream);

        document.open();
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.BLACK);
        Paragraph title = new Paragraph("Payment Receipt", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3f, 7f});
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);
        Color headerColor = new Color(97, 97, 97);

        table.addCell(createCell("Name", labelFont, headerColor));
        table.addCell(createCell(paymentDetailsMap.get("name"), valueFont, Color.WHITE));

        table.addCell(createCell("Mobile Number", labelFont, headerColor));
        table.addCell(createCell(paymentDetailsMap.get("mobile_number"), valueFont, Color.WHITE));

        table.addCell(createCell("Course Name", labelFont, headerColor));
        table.addCell(createCell(paymentDetailsMap.get("courseName"), valueFont, Color.WHITE));


        table.addCell(createCell("Course Genre", labelFont, headerColor));
        table.addCell(createCell(paymentDetailsMap.get("courseGenre"), valueFont, Color.WHITE));

        table.addCell(createCell("Amount", labelFont, headerColor));
        table.addCell(createCell(("Rs " + paymentDetailsMap.get("amount")), valueFont, Color.WHITE));

        table.addCell(createCell("Payment Date", labelFont, headerColor));
        table.addCell(createCell(paymentDetailsMap.get("paymentDate"), valueFont, Color.WHITE));

        table.addCell(createCell("Payment Mode", labelFont, headerColor));
        table.addCell(createCell(paymentDetailsMap.get("paymentMode"), valueFont, Color.WHITE));

        table.addCell(createCell("Transaction ID", labelFont, headerColor));
        table.addCell(createCell(paymentDetailsMap.get("transactionId"), valueFont, Color.WHITE));

        Color statusColor = new Color(76, 175, 80);
        Font statusFont = FontFactory.getFont(FontFactory.HELVETICA, 12, statusColor);
        table.addCell(createCell("Payment Status", labelFont, headerColor));
        PdfPCell pdfPCell = createCell("SUCCESS", statusFont, Color.WHITE);
        table.addCell(pdfPCell);

        document.add(table);

        Font noteFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, Color.GRAY);
        Paragraph note = new Paragraph("This is a system generated receipt.", noteFont);
        note.setAlignment(Element.ALIGN_CENTER);
        document.add(note);

        document.close();

        return byteArrayOutputStream.toByteArray();
    }

    private PdfPCell createCell(String content, Font font, Color backgroundColor) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBackgroundColor(backgroundColor);
        cell.setPadding(8f);
        cell.setBorderWidth(1f);
        return cell;
    }
}

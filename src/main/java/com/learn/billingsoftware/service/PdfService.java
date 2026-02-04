package com.learn.billingsoftware.service;

import com.learn.billingsoftware.entity.Invoice;
import com.learn.billingsoftware.entity.InvoiceItem;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public ByteArrayInputStream generateInvoicePdf(Invoice invoice) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            //  Header
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Paragraph title = new Paragraph("INVOICE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Invoice Details & Customer Info
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("Invoice ID: " + invoice.getId(), boldFont));
            document.add(new Paragraph("Date: " + invoice.getInvoiceDate().toString(), normalFont));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Bill To:", boldFont));
            document.add(new Paragraph("Customer: " + invoice.getCustomer().getName(), normalFont));
            document.add(new Paragraph("Phone: " + invoice.getCustomer().getPhone(), normalFont));
            document.add(new Paragraph("Address: " + invoice.getCustomer().getAddress(), normalFont));
            document.add(Chunk.NEWLINE);

            // Table Header
            PdfPTable table = new PdfPTable(4); // 4 Columns
            table.setWidthPercentage(100);
            table.setWidths(new int[]{4, 1, 2, 2}); // Relative widths

            addTableHeader(table, "Product");
            addTableHeader(table, "Qty");
            addTableHeader(table, "Price");
            addTableHeader(table, "Total");

            // Table Rows
            for (InvoiceItem item : invoice.getItems()) {
                table.addCell(item.getProduct().getName());
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell("Rs " + item.getPrice());
                table.addCell("Rs " + item.getTotal());
            }

            document.add(table);
            document.add(Chunk.NEWLINE);

            // Grand Totals
            Paragraph totals = new Paragraph();
            totals.setAlignment(Element.ALIGN_RIGHT);
            totals.add(new Paragraph("Total Amount: Rs " + invoice.getTotalAmount()));
            totals.add(new Paragraph("Tax: Rs " + invoice.getTotalTax()));
            totals.add(new Paragraph("Discount: - Rs " + invoice.getDiscount()));
            totals.add(new Paragraph("Grand Total: Rs " + invoice.getFinalAmount(), boldFont));

            document.add(totals);

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addTableHeader(PdfPTable table, String headerTitle) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
        header.setBorderWidth(1);
        header.setPhrase(new Phrase(headerTitle));
        table.addCell(header);
    }
}
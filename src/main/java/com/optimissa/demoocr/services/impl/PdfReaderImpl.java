package com.optimissa.demoocr.services.impl;

import com.optimissa.demoocr.services.IPdfReader;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PdfReaderImpl implements IPdfReader {

    @Override
    public String readTextFromPDF(String path) throws IOException {
        PDDocument document = PDDocument.load(new File(path));
        PDFTextStripper stripper = new PDFTextStripper();
        String strippedText = stripper.getText(document);

        // Check text exists into the file
        if (strippedText.trim().isEmpty()) {
            try {
                strippedText = extractTextFromScannedDocument(document);
            } catch (TesseractException te){
                strippedText = "";
                System.out.println("Error extrayendo texto de imagenes");
            }
        }

        return strippedText;
    }


    private String extractTextFromScannedDocument(PDDocument document)
            throws IOException, TesseractException {

        // Extract images from file
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        StringBuilder out = new StringBuilder();

        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata/");
        tesseract.setLanguage("spa"); // choose your language

        for (int page = 0; page < document.getNumberOfPages(); page++) {
            BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

            // Create a temp image file
            File temp = File.createTempFile("tempfile_" + page, ".png");
            ImageIO.write(bim, "png", temp);

            String result = tesseract.doOCR(temp);
            out.append(result);

            // Delete temp file
            temp.delete();
        }

        return out.toString();
    }
}

package com.optimissa.demoocr;

import com.optimissa.demoocr.services.IPdfReader;
import com.optimissa.demoocr.services.impl.PdfReaderImpl;

public class OcrMain {

    public static void main(String[] args){
        System.out.println("Inicia DEMO!!");

        IPdfReader pdfReader = new PdfReaderImpl();

        try {
            System.out.println("Leyendo fichero: " + args[0]);
            String text = pdfReader.readTextFromPDF(args[0]);
            System.out.println("Texto obtenido: " +  text);
        }catch (Exception e){
            System.out.println("Ocurrio un error");
            e.printStackTrace();
        }
    }
}

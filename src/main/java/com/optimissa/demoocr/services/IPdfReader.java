package com.optimissa.demoocr.services;

import java.io.IOException;

public interface IPdfReader {

    String readTextFromPDF(String path) throws IOException;
}

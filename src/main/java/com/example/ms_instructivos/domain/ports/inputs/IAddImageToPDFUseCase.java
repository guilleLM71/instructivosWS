package com.example.ms_instructivos.domain.ports.inputs;

import java.io.IOException;

public interface IAddImageToPDFUseCase {
    String generatePdfWithImage(String fileName) throws IOException;

}

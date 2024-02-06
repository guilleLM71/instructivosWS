package com.example.ms_instructivos.domain.ports.inputs;

import java.io.IOException;

public interface IPDFWaterMarkUseCase {
    String addWatermark(String inputFilePath, String outputFilePath, String watermarkText) throws IOException;
}

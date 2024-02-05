package com.example.ms_instructivos.domain.ports.inputs;

import java.io.IOException;

public interface IPDFWaterMark {
    String addWatermark(String inputFilePath, String outputFilePath, String watermarkText) throws IOException;
}

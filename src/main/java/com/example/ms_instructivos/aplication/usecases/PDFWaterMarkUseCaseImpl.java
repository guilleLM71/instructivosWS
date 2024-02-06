package com.example.ms_instructivos.aplication.usecases;

import com.aspose.pdf.Document;
import com.aspose.pdf.HorizontalAlignment;
import com.aspose.pdf.VerticalAlignment;
import com.aspose.pdf.WatermarkArtifact;
import com.aspose.pdf.facades.EncodingType;
import com.aspose.pdf.facades.FontStyle;
import com.aspose.pdf.facades.FormattedText;
import com.example.ms_instructivos.domain.ports.inputs.IPDFWaterMarkUseCase;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;

@Service
public class PDFWaterMarkUseCaseImpl implements IPDFWaterMarkUseCase {
    @Override
    public String addWatermark(String inputFilePath, String outputFilePath, String watermarkText) throws IOException {
        Document doc = new Document(inputFilePath);
        // Crear un texto formateado
        FormattedText formattedText = new FormattedText(watermarkText, Color.GRAY, FontStyle.CourierBold, EncodingType.Identity_h, true, 80.0F);
        // Crear artefacto de marca de agua y establecer sus propiedades
        WatermarkArtifact artifact = new WatermarkArtifact();
        artifact.setText(formattedText);
        artifact.setArtifactHorizontalAlignment(HorizontalAlignment.Center);
        artifact.setArtifactVerticalAlignment(VerticalAlignment.Center);
        artifact.setRotation(45);
        artifact.setOpacity(0.4);
        artifact.setBackground(false);
        for (int i = 1; i <= doc.getPages().size(); i++){
            doc.getPages().get_Item(i).getArtifacts().add(artifact);
        }
        doc.save(outputFilePath);
        return inputFilePath;
    }

}

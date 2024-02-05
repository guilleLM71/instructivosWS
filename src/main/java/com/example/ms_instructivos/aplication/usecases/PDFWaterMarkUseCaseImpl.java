package com.example.ms_instructivos.aplication.usecases;

import com.example.ms_instructivos.domain.ports.inputs.IPDFWaterMark;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
@Service
public class PDFWaterMarkUseCaseImpl implements IPDFWaterMark {
    @Override
    public String addWatermark(String inputFilePath, String outputFilePath, String watermarkText) throws IOException {
        // Cargar el documento PDF
        PDDocument document = PDDocument.load(new File(inputFilePath));

        // Configurar la fuente y el tamaño de la fuente para la marca de agua
        // Configurar la fuente y el tamaño de la fuente para la marca de agua
        PDType1Font font = PDType1Font.HELVETICA_BOLD;
        int fontSize = 48;

        // Establecer el color y la opacidad de la marca de agua
        Color color = new Color(128, 128, 128); // Gris medio
        float opacity = 0.3f; // Opacidad del 50%

        // Iterar sobre cada página del documento
        for (PDPage page : document.getPages()) {
            PDRectangle mediaBox = page.getMediaBox();
            float pageWidth = mediaBox.getWidth();
            float pageHeight = mediaBox.getHeight();

            // Crear el objeto de contenido de la página
            PDPageContentStream  contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);

            // Calcular la posición central para la marca de agua
            float centerX = pageWidth / 2;
            float centerY = pageHeight / 2;
            Matrix matrix = new Matrix();
            // Rotar la marca de agua si la página es horizontal
//            if (pageWidth > pageHeight) {
//                contentStream.transform(matrix.getRotateInstance(Math.toRadians(45), centerX, centerY));
//            }else {
//                 }
            contentStream.transform(matrix.getRotateInstance(Math.toRadians(45), centerX, centerY));

            // Establecer el color y la opacidad
            contentStream.setNonStrokingColor(color.getRed(), color.getGreen(), color.getBlue());
            PDExtendedGraphicsState pd = new PDExtendedGraphicsState();
            pd.setNonStrokingAlphaConstant(opacity);
            contentStream.setGraphicsStateParameters(pd);

            // Establecer la configuración de la marca de agua
            contentStream.setFont(font, fontSize);

            // Calcular la anchura del texto de la marca de agua
            float textWidth = font.getStringWidth(watermarkText) / 1000 * fontSize;

            // Calcular la posición de la marca de agua
            float startX = centerX - textWidth/0.5f ;
            float startY = centerY - fontSize/0.5f;

            // Agregar el texto de la marca de agua a la página
            contentStream.beginText();
            contentStream.newLineAtOffset(startX, startY);
            contentStream.showText(watermarkText);
            contentStream.endText();

            // Cerrar el objeto de contenido de la página
            contentStream.close();
        }

        // Guardar el documento modificado con la marca de agua
        document.save(new File(outputFilePath));

        // Cerrar el documento PDF
        document.close();
        return inputFilePath;
    }

}

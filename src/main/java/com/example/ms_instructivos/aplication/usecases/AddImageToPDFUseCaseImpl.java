package com.example.ms_instructivos.aplication.usecases;

import com.example.ms_instructivos.domain.ports.inputs.IAddImageToPDFUseCase;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Service
public class AddImageToPDFUseCaseImpl implements IAddImageToPDFUseCase{

    @Override
    public String generatePdfWithImage(String fileName) throws IOException {
        File file = new File("C://docs//" + fileName + ".pdf");
        String pathSave = "C://docsMod//"+fileName+".pdf";
        PDDocument document = PDDocument.load(file);
        PDImageXObject pdImage = PDImageXObject.createFromFileByContent(new File("C://docsQR//QR.png"), document);
        PDPage firstPage = document.getPage(0);
        PDPageContentStream contentStream = new PDPageContentStream(document, firstPage, PDPageContentStream.AppendMode.APPEND, true);
        contentStream.drawImage(pdImage, 450, 650, 100,100);
        contentStream.close();
        document.save(pathSave);
        document.close();
        return pathSave;
    }
}




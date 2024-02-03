package com.example.ms_instructivos.aplication.usecases;

import com.example.ms_instructivos.domain.ports.inputs.IGeneratorQRUseCase;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

@Service
public class GeneratorQRUseCaseImpl implements IGeneratorQRUseCase {
    @Override
    public void generarQR(String nombre) {
        String qrCodeText = nombre;
        String filePath = "C://docsQR//QR.png";
        int width = 300;
        int height = 300;
        try {
            generateQRCodeImage(qrCodeText, width, height, filePath);
        } catch (WriterException e) {
            System.out.println("No se pudo generar el QR, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("No se pudo generar el QR, IOException :: " + e.getMessage());
        }
    }

    public void generateQRCodeImage(String text,
                                           int width,
                                           int height,
                                           String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = Paths.get(filePath);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }


    public byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

}




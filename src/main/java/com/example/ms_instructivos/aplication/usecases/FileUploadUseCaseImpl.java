package com.example.ms_instructivos.aplication.usecases;

import com.example.ms_instructivos.domain.ports.inputs.IFileUploadUseCase;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class FileUploadUseCaseImpl implements IFileUploadUseCase {
    private final String UPLOAD_DIR = "C://docs//";
    @Override
    public String uploadFile(InputStream inputStream, String fileName) {
        Path path =null;
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            path = Paths.get(UPLOAD_DIR + "/" + fileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return path.toString();
    }


}

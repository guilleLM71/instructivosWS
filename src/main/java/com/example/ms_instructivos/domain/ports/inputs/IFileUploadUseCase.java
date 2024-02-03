package com.example.ms_instructivos.domain.ports.inputs;

import java.io.InputStream;

public interface IFileUploadUseCase {
    String uploadFile(InputStream inputStream, String fileName);

}

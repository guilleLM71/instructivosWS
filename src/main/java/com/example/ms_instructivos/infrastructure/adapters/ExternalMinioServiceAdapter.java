package com.example.ms_instructivos.infrastructure.adapters;

import com.example.ms_instructivos.domain.ports.outputs.ExternalMinioServicePort;
import com.example.ms_instructivos.infrastructure.config.MinioConfig;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.*;
import java.util.List;

@ApplicationScope
@Service
@RequiredArgsConstructor
public class ExternalMinioServiceAdapter implements ExternalMinioServicePort {
    @Autowired
    private MinioClient minioClient;

    @Value("${minio.buckek.name}")
    String defaultBucketName;

    @Value("${minio.default.folder}")
    String defaultBaseFolder;

    @Override
    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void uploadFile(String fileName, String folder) throws IOException {
        File file = new File(fileName);
        String path = folder + "/" + file.getName();
        FileInputStream fis = new FileInputStream(file);
        try {
            InputStream inputStream = fis;
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(defaultBucketName)
                    .object(path)
                    .stream(inputStream, inputStream.available(), -1)
                    .build());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String getFile(String fileName) {

        String path = "instructivos_escaneados/" + fileName;
        try {
            InputStream obj = minioClient.getObject(GetObjectArgs
                                                    .builder()
                                                    .bucket(defaultBucketName)
                                                    .object(path)
                                                    .build());

            //File destinationDir = new File("C://docs");
            File destinationFile = new File("C://docs", fileName);
            FileUtils.copyInputStreamToFile(obj, destinationFile);
            obj.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "C://docs//"+fileName;
    }

    @Override
    public String getPresignedObjectUrl(String fileName, String folder) {
        String path = folder + "/" + fileName;
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(defaultBucketName)
                    .method(Method.GET)
                    .object(path)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}



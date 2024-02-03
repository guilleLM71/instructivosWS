package com.example.ms_instructivos.infrastructure.adapters;
import com.example.ms_instructivos.domain.ports.outputs.ExternalMinioServicePort;
import com.example.ms_instructivos.infrastructure.config.MinioConfig;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.messages.Bucket;
import lombok.RequiredArgsConstructor;
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
        String path = folder+"/"+file.getName();
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
    public byte[] getFile(String key) {
        return new byte[0];
    }


//    @Override
//    public byte[] getFile(String key) {
//        try {
//            InputStream obj = minioClient.getObject(defaultBucketName, defaultBaseFolder + "/" + key);
//
//            byte[] content = IOUtils.toByteArray(obj);
//            obj.close();
//            return content;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}



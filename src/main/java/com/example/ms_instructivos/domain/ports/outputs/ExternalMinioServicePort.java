package com.example.ms_instructivos.domain.ports.outputs;

import io.minio.messages.Bucket;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.List;

public interface ExternalMinioServicePort {

    List<Bucket> getAllBuckets();

    void uploadFile(String fileName, String folder) throws IOException;

    byte[] getFile(String key);
}

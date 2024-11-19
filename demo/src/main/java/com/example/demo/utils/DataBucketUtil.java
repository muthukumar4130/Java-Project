package com.example.demo.utils;

import com.example.demo.exception.InternalException;
import com.example.demo.payload.request.FileDto;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class DataBucketUtil {

    @Value("${gcp.config.file}")
    private String gcpConfigFile;

    @Value("${gcp.project.id}")
    private String gcpProjectId;

    @Value("${gcp.bucket.id}")
    private String gcpBucketId;

    @Value("${gcp.dir.name}")
    private String gcpDirectoryName;

    public FileDto uploadFile(MultipartFile file, String fileName, String contentType) {
        try {
            byte[] fileData= FileUtil.readAsByteArray(convertFile(file));

            InputStream inputStream=new ClassPathResource(gcpConfigFile).getInputStream();

            StorageOptions options=StorageOptions.newBuilder().setProjectId(gcpProjectId)
                    .setCredentials(GoogleCredentials.fromStream(inputStream)).build();
            Storage storage= options.getService();
            Bucket bucket=storage.get(gcpBucketId,Storage.BucketGetOption.fields());

            String id = UUID.randomUUID().toString().substring(0, 6);

            log.info("id {}",id);

            Blob blob=bucket.create(gcpDirectoryName+"/"+fileName+"-" + id + checkFileExtension(fileName),fileData,contentType);

            if (blob !=null){
                return new FileDto(blob.getName(), Objects.requireNonNull(blob.getMetadata()).toString());
            }

        }catch (Exception ex){
            throw new RuntimeException();
        }
        throw new InternalException();
    }

    private String checkFileExtension(String fileName) {
        if (fileName !=null && fileName.contains(".")){
            String[] extensionList={".png",".jpeg",".pdf",".doc",".mp3"};

            for (String extension: extensionList){
                if (fileName.endsWith(extension)){
                    return extension;
                }
            }
        }
        throw new InvalidFileNameException("message","Not a permitted file type");
    }

    private File convertFile(MultipartFile file) {
        try {
           if (file.getOriginalFilename() == null){
                throw new RuntimeException();
           }
           File file1=new File(file.getOriginalFilename());
            FileOutputStream fileOutputStream=new FileOutputStream(file1);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
            return file1;
        }catch (Exception ex){
            throw new RuntimeException();
        }
    }
}

package cn.batenglish.gallery.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Service
public class UploadService {
    @Value("${upload.dir}")
    private String uploadDir;

    public String uploadImage(MultipartFile file) throws IOException, URISyntaxException {
        String fileName = file.getOriginalFilename();
                String extension = fileName.substring(fileName.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + extension;

        Files.write(Paths.get(new URI(uploadDir + newFilename)), file.getBytes(), StandardOpenOption.CREATE_NEW);
        return newFilename;
    }
}

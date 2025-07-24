package com.library.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

public class ImageUploadUtil {
    public static String saveImageWithUniqueName (MultipartFile file, String uploadDir) throws IOException {
        String originalName = Paths.get(Objects.requireNonNull(file.getOriginalFilename()))
                .getFileName().toString();
        String fileName = UUID.randomUUID() + "_" + originalName;

        Path uploadPath = Paths.get(uploadDir);
        Files.createDirectories(uploadPath);

        Path filePath = uploadPath.resolve(fileName);
        file.transferTo(filePath);

        return fileName;
    }
}

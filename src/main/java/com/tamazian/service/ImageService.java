package com.tamazian.service;

import com.tamazian.util.PropertiesUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageService {

    private static final ImageService INSTANCE = new ImageService();
    private final String basePath = PropertiesUtil.get("image.base.url");

    public static ImageService getInstance(){
        return INSTANCE;
    }

    @SneakyThrows
    public void upload(String imagePath, InputStream imageContent){
        Path imageFullPath = Path.of(basePath, imagePath);

        try(imageContent) {
            Files.createDirectories(imageFullPath.getParent());
            Files.write(imageFullPath, imageContent.readAllBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public Optional<InputStream> get(String imagePath){
        Path imageFullPath = Path.of(basePath, imagePath);
        return Files.exists(imageFullPath)
                ? Optional.of(Files.newInputStream(imageFullPath))
                : Optional.empty();
    }
}

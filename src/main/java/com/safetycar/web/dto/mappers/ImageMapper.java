package com.safetycar.web.dto.mappers;

import com.safetycar.exceptions.FileStorageException;
import com.safetycar.models.Image;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class ImageMapper {

    public Image fromMultipart(MultipartFile file) {
        try {
            String imageName = file.getOriginalFilename();
            return new Image(imageName, file.getContentType(), file.getBytes());
        } catch (IOException | NullPointerException e) {
            throw new FileStorageException(e.getMessage());
        }
    }

    public Image updateFromMultipart(Image image, MultipartFile file) {
        try {
            String imageName = file.getOriginalFilename();
            image.setImageName(imageName);
            image.setImageType(file.getContentType());
            image.setData(file.getBytes());
            return image;
        } catch (IOException | NullPointerException e) {
            throw new FileStorageException(e.getMessage());
        }
    }
}

package com.safetycar.services;

import com.safetycar.exceptions.FileStorageException;
import com.safetycar.models.Image;
import com.safetycar.repositories.ImageRepository;
import com.safetycar.services.contracts.ImageService;
import com.safetycar.services.contracts.base.GetService;
import com.safetycar.services.contracts.base.GetServiceBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImageServiceImpl extends GetServiceBase implements ImageService, GetService {

    private final ImageRepository imageRepository;
    private static final Log LOGGER = LogFactory.getLog(ImageServiceImpl.class);

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Transactional
    public void saveFile(MultipartFile file) {
        String imageName = file.getOriginalFilename();
        try {
            Image image = new Image(imageName, file.getContentType(), file.getBytes());
            imageRepository.save(image);
        } catch (Exception e) {
            LOGGER.warn(e);
            throw new FileStorageException(e.getMessage());
        }

    }

    public Image getFile(int fileId) {
        return get(imageRepository, fileId, Image.class);
    }

    public List<Image> getFiles() {
        return imageRepository.findAll();
    }

}

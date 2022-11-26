package com.safetycar.services.contracts;

import com.safetycar.models.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void saveFile(MultipartFile file);

    Image getFile(int fileId);


}

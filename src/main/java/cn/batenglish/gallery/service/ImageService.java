package cn.batenglish.gallery.service;

import cn.batenglish.gallery.entity.Image;
import cn.batenglish.gallery.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private final ImageRepository imageRepository;
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }
public Image findById(String id){
   return imageRepository.findById(Integer.parseInt(id)).orElseThrow(() -> new EntityNotFoundException("Image not found"));
}
    public Image getNewImage(){
        return new Image();
    }
    public void saveImage(Image image){
        imageRepository.save(image);
    }
    public List<Image> getImagesSince(Timestamp since){
        try{
            List images = imageRepository.findAllByUploadTimeGreaterThan(since);
            return images;
        }catch(Exception e){
            throw new RuntimeException("Error fetching images since " + since, e);
        }
    }
    //getImagesByUserIdSince
    public List<Image> getImagesByUserIdSince(Integer userId, Timestamp since){
        List images = imageRepository.findAllByUploaderIdAndUploadTimeGreaterThan(userId,since);
//       System.out.println(images.toString());
        return images;
    }
    public void removeImageById(Integer image_id){
        imageRepository.deleteById(image_id);
    }
}

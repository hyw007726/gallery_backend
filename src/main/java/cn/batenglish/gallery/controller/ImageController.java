package cn.batenglish.gallery.controller;

import cn.batenglish.gallery.entity.Image;
import cn.batenglish.gallery.entity.User;
import cn.batenglish.gallery.service.ImageService;
import cn.batenglish.gallery.service.SessionService;
import cn.batenglish.gallery.service.UploadService;
import cn.batenglish.gallery.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(path="/api") // This means URL's start with /demo (after Application path)
public class ImageController {

    private final ImageService imageService; // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private final UserService userService;
    private final UploadService uploadService;
    private final SessionService sessionService;
    @Autowired
    public ImageController(SessionService sessionService,UserService userService, UploadService uploadService, ImageService imageService) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.imageService=imageService;
        this.sessionService=sessionService;
    }
    @DeleteMapping("/removeImageById")
    public ResponseEntity<String> removeImagesByUser(@RequestParam("imageId") String image_id){
        imageService.removeImageById(Integer.parseInt(image_id));
        return ResponseEntity.status(HttpStatus.OK).body("Removed");
    }
    @GetMapping("/getImagesByUser")
    public ResponseEntity<List<Image>> getImagesByUser(@RequestParam("since") String timeStamp,@RequestParam("id") String id){
        Timestamp since = new Timestamp(Long.parseLong(timeStamp));
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getImagesByUserIdSince(Integer.parseInt(id),since));
    }
    @GetMapping("/getImages")
    public ResponseEntity<List<Image>> getImages(@RequestParam("since") String timeStamp){

    try{
        Timestamp since = new Timestamp(Long.parseLong(timeStamp));
        List<Image> images = imageService.getImagesSince(since);
        return ResponseEntity.status(HttpStatus.OK).body(images);
    }catch (Exception e) {
        // Log the exception if necessary
         e.printStackTrace();
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    }
    @Value("${upload.dir}")
    private String uploadDir;
    @PostMapping("/uploadGenerated")
    @ResponseBody
    public String uploadGenerated(@RequestBody Map<String, String> requestBody){
        String id = requestBody.get("id");
        String prompt = requestBody.get("prompt");
        String generated = requestBody.get("generated");

        Optional<User> opUser = userService.findById(Integer.parseInt(id));
        if(opUser.isPresent()) {
            User currentUser = opUser.get();
        try {
            URL url = new URL(generated);
            BufferedInputStream in = new BufferedInputStream(url.openStream());
            String filename=UUID.randomUUID().toString()+ ".png";
            String filePath = uploadDir + filename ;
            System.out.println(filePath);
            File file = new File(filePath);
            FileOutputStream out = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            in.close();
            Image currentImage = imageService.getNewImage();
            currentImage.setFilename(filename);
            currentImage.setCaption(prompt);
            currentImage.setUploader(currentUser);
            imageService.saveImage(currentImage);
            return "Image downloaded successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to download image";
        }
        }else{
            return "User not found";
        }
    }
    @PostMapping(value = "/uploadImages", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadImages(@RequestPart("captions") String captions, HttpServletRequest request, @NotBlank @RequestHeader("Authorization") String id, @RequestParam("images") List<MultipartFile> files) throws IOException, URISyntaxException {

        ObjectMapper objectMapper = new ObjectMapper();
        String[] captionsArray = objectMapper.readValue(captions, String[].class);
        System.out.println("captions length:"+captionsArray.length);
        System.out.println("files length:"+files.size());

//        System.out.println(user);
        Optional<User> opUser = userService.findById(Integer.parseInt(id));
        if(opUser.isPresent()){
            User currentUser=opUser.get();
            Integer index=0;
            for(MultipartFile file : files){
                Image currentImage = imageService.getNewImage();
                String filename= uploadService.uploadImage(file);
                currentImage.setFilename(filename);
                currentImage.setCaption(captionsArray[index]);
                currentImage.setUploader(currentUser);
                imageService.saveImage(currentImage);
                index++;
            }
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File uploaded failed");
    }

}

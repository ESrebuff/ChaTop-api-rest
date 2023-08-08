package chatop.apiRest.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/picture")
public class ImageController {

    @GetMapping("/{name}")
    public ResponseEntity<Object> getPictureByName(@PathVariable String name) throws IOException {
        String imagePath = "D:\\OpenClassrooms D\u00E9veloppeur Full-Stack - Java et Angular\\P3\\Ch\u00E0Top\\ChaTop-api-rest\\src\\main\\resources\\static\\picture\\" + name;
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            return ResponseEntity.notFound().build();
        }
        InputStream inputStream = new FileInputStream(imageFile);
        byte[] imageBytes = FileCopyUtils.copyToByteArray(inputStream);
            String contentType = MediaType.IMAGE_JPEG_VALUE;
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(imageBytes);
        }
}

package chatop.apiRest.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/picture")
public class ImageController {

    @GetMapping("/{name}")
    public ResponseEntity<Object> getPictureByName(@PathVariable String name) throws IOException {
        String imagePath = "static/" + name;
        Resource imageResource = new ClassPathResource(imagePath);
        System.out.println(imagePath);
        System.out.println(imageResource);
        if (!imageResource.exists()) {
            return ResponseEntity.notFound().build();
        }
        InputStream inputStream = imageResource.getInputStream();
        byte[] imageBytes = FileCopyUtils.copyToByteArray(inputStream);
            String contentType = MediaType.IMAGE_JPEG_VALUE;
            
            return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .body(imageBytes);
        }
}

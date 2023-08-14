package chatop.apiRest.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/picture")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Images")
public class ImageController {

    @Operation(summary = "Get an image by name", description = "Retrieves an image based on the provided name.")
    @ApiResponses({
            @ApiResponse(description = "Image retrieved successfully.", responseCode = "200", content = @Content(mediaType = MediaType.IMAGE_JPEG_VALUE)),
            @ApiResponse(description = "You do not have authorization to access this resource.", responseCode = "403", content = @Content),
            @ApiResponse(description = "Image not found.", responseCode = "404", content = @Content),
    })
    @GetMapping("/{name}")
    public ResponseEntity<Object> getPictureByName(@PathVariable String name) throws IOException {
        String imagePath = "D:\\OpenClassrooms D\u00E9veloppeur Full-Stack - Java et Angular\\P3\\Ch\u00E0Top\\ChaTop-api-rest\\src\\main\\resources\\static\\picture\\"
                + name;
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

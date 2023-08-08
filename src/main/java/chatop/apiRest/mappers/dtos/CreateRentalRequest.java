package chatop.apiRest.mappers.dtos;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRentalRequest {

    private String name;
    private Double surface;
    private Double price;
    private MultipartFile picture;
    private String description;
    
}

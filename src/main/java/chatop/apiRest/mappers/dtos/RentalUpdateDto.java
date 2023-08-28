package chatop.apiRest.mappers.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalUpdateDto {

    private String name;
    private Double surface;
    private Double price;
    private String description;
    private LocalDateTime updated_at;
    
}

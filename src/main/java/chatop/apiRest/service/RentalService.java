package chatop.apiRest.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import chatop.apiRest.mappers.dtos.RentalDto;
import chatop.apiRest.mappers.dtos.RentalUpdateDto;
import chatop.apiRest.modele.Rental;

public interface RentalService {
    
    RentalDto create(Rental rental, Integer userId);

    Map<String, List<RentalDto>> getRentals();

    RentalDto getRental(Integer id);

    Rental update(Integer id, RentalUpdateDto rentalUpdateDto, Integer userId);

    String uploadPicture(MultipartFile imageFile);

}

package chatop.apiRest.service;

import java.util.List;

import chatop.apiRest.mappers.dtos.RentalDto;
import chatop.apiRest.modele.Rental;

public interface RentalService {
    
    RentalDto create(Rental rental, Integer userId);

    List<RentalDto> getRentals();

    RentalDto getRental(Integer id);

    Rental update(Integer id, Rental rental);

}

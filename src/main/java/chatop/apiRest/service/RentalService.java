package chatop.apiRest.service;

import java.util.List;

import chatop.apiRest.modele.Rental;

public interface RentalService {
    
    Rental create(Rental rental);

    List<Rental> getRentals();

    Rental getRental(Integer id);

    Rental update(Integer id, Rental rental);

    String delete(Integer id);

}

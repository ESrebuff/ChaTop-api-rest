package chatop.apiRest.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import chatop.apiRest.modele.Rental;
import chatop.apiRest.modele.User;
import chatop.apiRest.repository.RentalRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;

    @Override
    public Rental create(Rental rental) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        rental.setOwnerId(currentUser.getId());

        return rentalRepository.save(rental);
    }

    @Override
    public List<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    @Override
    public Rental getRental(Integer id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rental not found for id: " + id));
    }

    @Override
    public Rental update(Integer id, Rental rental) {
        return rentalRepository.findById(id)
                .map(r -> {
                    r.setName(r.getName());
                    r.setSurface(r.getSurface());
                    r.setPrice(r.getPrice());
                    r.setPicture(r.getPicture());
                    r.setDescription(r.getDescription());
                    return rentalRepository.save(r);
                }).orElseThrow(() -> new RuntimeException("Rental not found"));
    }

    @Override
    public String delete(Integer id) {
        rentalRepository.deleteById(id);
        return "Rental deleted";
    }

}

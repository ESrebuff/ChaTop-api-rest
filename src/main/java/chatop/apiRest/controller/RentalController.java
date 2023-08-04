package chatop.apiRest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chatop.apiRest.modele.Rental;
import chatop.apiRest.service.RentalService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/rentals")
@AllArgsConstructor
public class RentalController {
    
    private final RentalService rentalService;

    @PostMapping
    public Rental create(@RequestBody Rental doRental) {
        return rentalService.create(doRental);
    }

    @GetMapping
    public List<Rental> getRentals() {
        return rentalService.getRentals();
    }

    @GetMapping("/{id}")
    public Rental getRental(@PathVariable Integer id) {
        return rentalService.getRental(id);
    }

    @PutMapping("/{id}")
    public Rental update(@PathVariable Integer id, @RequestBody Rental rental) {
        return rentalService.update(id, rental);
    }

}

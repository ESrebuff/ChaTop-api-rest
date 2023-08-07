package chatop.apiRest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chatop.apiRest.mappers.dtos.RentalDto;
import chatop.apiRest.modele.Rental;
import chatop.apiRest.service.RentalService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/rentals")
@AllArgsConstructor
public class RentalController {
    
    private final ModelMapper modelMapper;
    private final RentalService rentalService;

    @PostMapping
    public Rental create(@RequestBody RentalDto rentalDto) {
        Rental rental = modelMapper.map(rentalDto, Rental.class);
        return rentalService.create(rental);
    }

    @GetMapping
    public List<RentalDto> getRentals() {
    List<Rental> rentals = rentalService.getRentals();
    return rentals.stream()
                  .map(rental -> modelMapper.map(rental, RentalDto.class))
                  .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public RentalDto getRental(@PathVariable Integer id) {
    Rental rental = rentalService.getRental(id);
    RentalDto rentalDto = modelMapper.map(rental, RentalDto.class);
    return rentalDto;
    }

    @PutMapping("/{id}")
    public Rental update(@PathVariable Integer id, @RequestBody Rental rental) {
        return rentalService.update(id, rental);
    }

}

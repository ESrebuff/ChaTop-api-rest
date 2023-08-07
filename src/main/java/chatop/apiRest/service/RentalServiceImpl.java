package chatop.apiRest.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import chatop.apiRest.mappers.dtos.RentalDto;
import chatop.apiRest.modele.Rental;
import chatop.apiRest.repository.RentalRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureModelMapper() {
        modelMapper.createTypeMap(Rental.class, RentalDto.class)
                .addMapping(Rental::getOwnerId, RentalDto::setOwner_id)
                .addMapping(Rental::getCreatedAt, RentalDto::setCreated_at)
                .addMapping(Rental::getUpdatedAt, RentalDto::setUpdated_at);
    }

    private RentalDto mapToRentalDto(Rental rental) {
        return modelMapper.map(rental, RentalDto.class);
    }

    @Override
    public RentalDto create(Rental rental, Integer userId) {
        rental.setOwnerId(userId);
        Rental rentalsavec = rentalRepository.save(rental);
        RentalDto rentalDto = mapToRentalDto(rentalsavec);
        return rentalDto;
    }

    @Override
    public List<RentalDto> getRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        return rentals.stream()
                .map(this::mapToRentalDto)
                .collect(Collectors.toList());
    }

    @Override
    public RentalDto getRental(Integer id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rental not found for id: " + id));
        return mapToRentalDto(rental);
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

}

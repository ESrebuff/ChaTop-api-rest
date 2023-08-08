package chatop.apiRest.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import chatop.apiRest.mappers.dtos.RentalDto;
import chatop.apiRest.mappers.dtos.RentalUpdateDto;
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
        Rental rentalSaved = rentalRepository.save(rental);
        RentalDto rentalDto = mapToRentalDto(rentalSaved);
        return rentalDto;
    }

    @Override
    public Map<String, List<RentalDto>> getRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        List<RentalDto> rentalDtos = rentals.stream()
                .map(this::mapToRentalDto)
                .collect(Collectors.toList());

        Map<String, List<RentalDto>> response = new HashMap<>();
        response.put("rentals", rentalDtos);

        return response;
    }

    @Override
    public RentalDto getRental(Integer id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rental not found for id: " + id));
        return mapToRentalDto(rental);
    }

    @Override
    public Rental update(Integer id, RentalUpdateDto rentalUpdateDto, Integer userId) {
        RentalDto rentalDto = getRental(id);
        if (rentalDto.getOwner_id().equals(userId)) {
            return rentalRepository.findById(id).map(existingRental -> {
                    existingRental.setName(rentalUpdateDto.getName());
                    existingRental.setSurface(rentalUpdateDto.getSurface());
                    existingRental.setPrice(rentalUpdateDto.getPrice());
                    existingRental.setDescription(rentalUpdateDto.getDescription());
                    existingRental.setUpdatedAt(LocalDateTime.now());
                    return rentalRepository.save(existingRental);
                })
                .orElseThrow(() -> new RuntimeException("Rental not found"));
        }
        return null;
    }

}

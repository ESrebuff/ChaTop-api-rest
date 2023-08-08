package chatop.apiRest.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    
    @Autowired
    private ResourceLoader resourceLoader;

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
                return rentalRepository.save(existingRental);
            })
                    .orElseThrow(() -> new RuntimeException("Rental not found"));
        }
        return null;
    }

    @Override
    public String uploadPicture(MultipartFile imageFile) {
        try {
            String uploadPath = "D:\\OpenClassrooms Développeur Full-Stack - Java et Angular\\P3\\ChàTop\\ChaTop-api-rest\\src\\main\\resources\\static\\picture\\";
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            String filePath = uploadPath + fileName;
            File pictureDir = ResourceUtils.getFile(uploadPath);
            if (!pictureDir.exists()) {
                pictureDir.mkdirs();
            }
            imageFile.transferTo(new File(filePath));
            String addressAndPort = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            String imageUrl = addressAndPort + "/picture/" + fileName;
            return imageUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}

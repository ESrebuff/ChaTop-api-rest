package chatop.apiRest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import chatop.apiRest.jsonWebToken.JwtService;
import chatop.apiRest.mappers.dtos.RentalDto;
import chatop.apiRest.mappers.dtos.RentalUpdateDto;
import chatop.apiRest.modele.Rental;
import chatop.apiRest.modele.User;
import chatop.apiRest.repository.UserRepository;
import chatop.apiRest.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/rentals")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Rentals")
public class RentalController {

    private final RentalService rentalService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Operation(summary = "Create a rental", description = "Creates a new rental based on the provided information.")
    @ApiResponses({
            @ApiResponse(description = "Rental created !", responseCode = "200", content = @Content),
            @ApiResponse(description = "You do not have authorization to access this resource.", responseCode = "403", content = @Content),
    })
    @PostMapping
    public ResponseEntity<Object> createRental(
            HttpServletRequest request,
            @RequestParam("name") String name,
            @RequestParam("surface") Double surface,
            @RequestParam("price") Double price,
            @RequestParam("picture") MultipartFile picture,
            @RequestParam("description") String description) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        if (token != null) {
            String email = jwtService.getUsernameFromToken(token);
            User userRepos = userRepository.findByUsername(email).orElse(null);
            Integer userId = userRepos.getId();
            String pictureUrl = rentalService.uploadPicture(picture);
            if (pictureUrl == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            Rental rental = new Rental();
            rental.setName(name);
            rental.setSurface(surface);
            rental.setPrice(price);
            rental.setDescription(description);
            rental.setPicture(pictureUrl);
            rentalService.create(rental, userId);
            return ResponseEntity.ok(Map.of("message", "Rental created !"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Get list of rentals", description = "Retrieves a list of rentals.")
    @ApiResponses({
            @ApiResponse(description = "List of rentals retrieved successfully.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = RentalDto.class)))),
            @ApiResponse(description = "You do not have authorization to access this resource.", responseCode = "403", content = @Content),
    })
    @GetMapping
    public Map<String, List<RentalDto>> getRentals() {
        return rentalService.getRentals();
    }

    @Operation(summary = "Retrieve a rental by ID", description = "Retrieves detailed information about a rental based on their ID.")
    @ApiResponses({
            @ApiResponse(description = "Rental information retrieved successfully.", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalDto.class))),
            @ApiResponse(description = "You do not have authorization to access this resource.", responseCode = "403", content = @Content),
            @ApiResponse(description = "The requested user was not found.", responseCode = "404", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<RentalDto> getRental(@PathVariable Integer id) {
        RentalDto rentalDto = rentalService.getRental(id);
        if (rentalDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rentalDto);
    }

    @Operation(summary = "Update a rental", description = "Updates detailed information about a rental based on its ID.")
    @ApiResponses({
            @ApiResponse(description = "Rental updated !", responseCode = "200", content = @Content),
            @ApiResponse(description = "You do not have authorization to access this resource.", responseCode = "403", content = @Content),
            @ApiResponse(description = "The requested rental was not found.", responseCode = "404", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> update(
            HttpServletRequest request,
            @PathVariable Integer id,
            @ModelAttribute @Validated RentalUpdateDto rentalUpdateDto) {

        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        Integer userId = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }

        if (token != null) {
            String email = jwtService.getUsernameFromToken(token);
            User userRepos = userRepository.findByUsername(email).orElse(null);
            userId = userRepos.getId();
        }

        Rental updatedRental = rentalService.update(id, rentalUpdateDto, userId);
        if (updatedRental == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("message", "Rental updated !"));
    }

}

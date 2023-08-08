package chatop.apiRest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chatop.apiRest.jsonWebToken.JwtService;
import chatop.apiRest.mappers.dtos.RentalDto;
import chatop.apiRest.mappers.dtos.RentalUpdateDto;
import chatop.apiRest.mappers.dtos.UserDto;
import chatop.apiRest.modele.Rental;
import chatop.apiRest.modele.User;
import chatop.apiRest.repository.UserRepository;
import chatop.apiRest.service.RentalService;
import chatop.apiRest.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/rentals")
@AllArgsConstructor
public class RentalController {

    private final RentalService rentalService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createRental(HttpServletRequest request, @RequestBody Rental rental) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        if (token != null) {
            String email = jwtService.getUsernameFromToken(token);
            User userRepos = userRepository.findByUsername(email).orElse(null);
            Integer userId = userRepos.getId();
            rentalService.create(rental, userId);
            return ResponseEntity.ok(Map.of("message", "Rental created !"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @GetMapping
    public Map<String, List<RentalDto>> getRentals() {
        return rentalService.getRentals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDto> getRental(@PathVariable Integer id) {
        RentalDto rentalDto = rentalService.getRental(id);
        if (rentalDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rentalDto);
    }

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

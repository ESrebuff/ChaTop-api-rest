package chatop.apiRest.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chatop.apiRest.jsonWebToken.JwtService;
import chatop.apiRest.mappers.dtos.AuthResponseDto;
import chatop.apiRest.mappers.dtos.LoginRequestDto;
import chatop.apiRest.mappers.dtos.RegisterRequestDto;
import chatop.apiRest.mappers.dtos.UserDto;
import chatop.apiRest.modele.User;
import chatop.apiRest.repository.UserRepository;
import chatop.apiRest.service.AuthServiceImpl;
import chatop.apiRest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthServiceImpl authService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Operation(summary = "User login", description = "Logs in a user based on the provided login request.")
    @ApiResponses({
            @ApiResponse(description = "User logged in successfully.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AuthResponseDto.class))),
            @ApiResponse(description = "You do not have authorization to access this resource.", responseCode = "403", content = @Content),
    })
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Register a new user", description = "Registers a new user based on the provided registration request.")
    @ApiResponses({
            @ApiResponse(description = "User registered successfully.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AuthResponseDto.class))),
            @ApiResponse(description = "You do not have authorization to access this resource.", responseCode = "403", content = @Content),
    })
    @PostMapping(value = "register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto request) {
        if (!isValidEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(AuthResponseDto.builder()
                            .build());
        }
        return ResponseEntity.ok(authService.register(request));
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get current user's information", description = "Retrieves information about the currently authenticated user.")
    @ApiResponses({
            @ApiResponse(description = "User information retrieved successfully.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(description = "You do not have authorization to access this resource.", responseCode = "403", content = @Content)
    })
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        if (token != null) {
            String email = jwtService.getUsernameFromToken(token);
            User userRepos = userRepository.findByUsername(email).orElse(null);
            UserDto userDto = userService.getUserById(userRepos.getId());
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // Méthode pour valider le format de l'adresse e-mail
    private boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

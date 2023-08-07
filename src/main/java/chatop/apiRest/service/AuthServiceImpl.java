package chatop.apiRest.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import chatop.apiRest.jsonWebToken.JwtService;
import chatop.apiRest.mappers.dtos.AuthResponseDto;
import chatop.apiRest.mappers.dtos.LoginRequestDto;
import chatop.apiRest.mappers.dtos.RegisterRequestDto;
import chatop.apiRest.modele.Role;
import chatop.apiRest.modele.User;
import chatop.apiRest.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponseDto.builder()
            .token(token)
            .build();

    }


    @Override
    public AuthResponseDto register(RegisterRequestDto request) {
        User user = User.builder()
            .username(request.getEmail())
            .password(passwordEncoder.encode( request.getPassword()))
            .firstname(request.getName())
            .role(Role.USER)
            .build();

        userRepository.save(user);
        return AuthResponseDto.builder()
            .token(jwtService.getToken(user))
            .build();
        
    }

}

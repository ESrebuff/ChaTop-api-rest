package chatop.apiRest.service;

import chatop.apiRest.mappers.dtos.AuthResponseDto;
import chatop.apiRest.mappers.dtos.LoginRequestDto;
import chatop.apiRest.mappers.dtos.RegisterRequestDto;

public interface AuthService {

    AuthResponseDto login(LoginRequestDto request);
    
    AuthResponseDto register(RegisterRequestDto request);

}

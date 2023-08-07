package chatop.apiRest.service;

import chatop.apiRest.mappers.dtos.UserDto;

public interface UserService {

    UserDto getUserById(Integer id);
    
}

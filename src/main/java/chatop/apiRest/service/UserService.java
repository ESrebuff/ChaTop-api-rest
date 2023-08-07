package chatop.apiRest.service;

import chatop.apiRest.mappers.dtos.UserDto;
import chatop.apiRest.modele.User;

public interface UserService {

    User getUserById(Integer id);

    UserDto getUserDtoByUser(User user);
    
}

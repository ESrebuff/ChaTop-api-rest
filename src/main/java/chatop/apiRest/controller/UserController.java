package chatop.apiRest.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chatop.apiRest.mappers.dtos.UserDto;
import chatop.apiRest.modele.user.User;
import chatop.apiRest.service.UserService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Integer id) {
    User user = userService.getUserById(id);
    UserDto rentalDto = modelMapper.map(user, UserDto.class);
    return rentalDto;
    }
    
}

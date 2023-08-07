package chatop.apiRest.service;

import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import chatop.apiRest.mappers.dtos.UserDto;
import chatop.apiRest.modele.User;
import chatop.apiRest.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rental not found for id: " + id));
    }


    public UserDto getUserDtoByUser(User user) {
        modelMapper.typeMap(User.class, UserDto.class)
                   .addMappings(new PropertyMap<User, UserDto>() {
                       @Override
                       protected void configure() {
                           map().setEmail(source.getUsername());
                           map().setName(source.getFirstname());
                           map().setCreated_at(source.getCreatedAt());
                           map().setUpdated_at(source.getUpdatedAt());
                       }
                   });
        
        return modelMapper.map(user, UserDto.class);
    }


    
}

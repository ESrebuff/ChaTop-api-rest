package chatop.apiRest.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import chatop.apiRest.modele.User;
import chatop.apiRest.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rental not found for id: " + id));
    }
    
}

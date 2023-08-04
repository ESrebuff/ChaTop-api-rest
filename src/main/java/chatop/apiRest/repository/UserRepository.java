package chatop.apiRest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import chatop.apiRest.modele.user.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username); 
}

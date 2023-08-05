package chatop.apiRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chatop.apiRest.modele.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{
    
}

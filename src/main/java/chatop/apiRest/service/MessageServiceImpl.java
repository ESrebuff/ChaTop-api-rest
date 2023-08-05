package chatop.apiRest.service;

import org.springframework.stereotype.Service;

import chatop.apiRest.modele.Message;
import chatop.apiRest.repository.MessageRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService{

    public final MessageRepository messageRepository;

    @Override
    public Message create(Message message) {
        return messageRepository.save(message);
    }
    
}

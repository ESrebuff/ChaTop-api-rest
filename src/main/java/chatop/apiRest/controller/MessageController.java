package chatop.apiRest.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chatop.apiRest.modele.Message;
import chatop.apiRest.service.MessageService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {
    
    private final MessageService messageService;

    public Message create(@RequestBody Message doMessage) {
        return messageService.create(doMessage);
    }

}

package chatop.apiRest.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chatop.apiRest.modele.Message;
import chatop.apiRest.service.MessageService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor
public class MessageController {
    
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Message message) {
        Message messageSaved = messageService.create(message);
        if (messageSaved != null) {
            return ResponseEntity.ok(Map.of("message", "Message send with success"));
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

}

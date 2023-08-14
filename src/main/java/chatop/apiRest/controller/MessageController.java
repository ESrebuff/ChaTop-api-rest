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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Messages")
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "Send a message", description = "Sends a new message based on the provided information.")
    @ApiResponses({
            @ApiResponse(description = "Message send with success", responseCode = "200", content = @Content),
            @ApiResponse(description = "You do not have authorization to access this resource.", responseCode = "403", content = @Content),
    })
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Message message) {
        Message messageSaved = messageService.create(message);
        if (messageSaved != null) {
            return ResponseEntity.ok(Map.of("message", "Message send with success"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

}

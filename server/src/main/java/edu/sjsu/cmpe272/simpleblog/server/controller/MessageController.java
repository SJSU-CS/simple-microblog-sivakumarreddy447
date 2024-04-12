package edu.sjsu.cmpe272.simpleblog.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    /**
     * Creates a new message after verifying its digital signature.
     *
     * @param messageEntry The message details submitted for creation.
     * @return ResponseEntity with either the message ID or an error message.
     */
    @PostMapping("/create")
    public ResponseEntity<?> createMessage(@RequestBody MessageEntry messageEntry) {
        try {
            Long messageId = messageService.processMessageCreation(messageEntry);
            if (messageId != null) {
                logger.info("Message created successfully with ID: {}", messageId);
                return ResponseEntity.ok(Map.of("messageId", messageId));
            } else {
                logger.warn("Signature verification failed for message from author: {}", messageEntry.getAuthor());
                return ResponseEntity.badRequest().body(Map.of("error", "Signature verification failed"));
            }
        } catch (Exception e) {
            logger.error("Error creating message: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("error", "Internal server error"));
        }
    }

    /**
     * Lists messages with optional pagination.
     *
     * @param params Pagination parameters 'limit', 'next', and 'startingId'.
     * @return ResponseEntity with a list of messages or an error message.
     */
    @PostMapping("/list")
    public ResponseEntity<?> listMessages(@RequestBody Map<String, Object> params) {
        try {
            List<MessageEntry> messages = messageService.listMessagesWithPagination(params);
            logger.info("Messages listed successfully");
            return ResponseEntity.ok(messages);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request parameters: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error listing messages: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("error", "Internal server error"));
        }
    }
}

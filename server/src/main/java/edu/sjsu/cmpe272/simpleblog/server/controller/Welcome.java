package edu.sjsu.cmpe272.simpleblog.server.controller;


import edu.sjsu.cmpe272.simpleblog.server.MessageJpaRepo;
import edu.sjsu.cmpe272.simpleblog.server.Messages;
import edu.sjsu.cmpe272.simpleblog.server.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@Controller
@RestController
public class Welcome {
    @GetMapping("/")
    ResponseEntity<String> getWelcome() {
        return ResponseEntity.ok("Welcome!");
    }

    @RestController
    @RequestMapping("/messages")
    static class MessageController {

        private static final Logger log = LoggerFactory.getLogger(MessageController.class);

        @Autowired
        private MessageJpaRepo messageJpaRepo;


        @PostMapping("/create")
        public ResponseEntity<?> createMessage(@RequestBody Messages message) throws Exception {
            Messages savedMessage = messageJpaRepo.save(message);
            return ResponseEntity.ok(Map.of("message-id", savedMessage.getMessageId()));
        }

        @PostMapping("/list")
        public ResponseEntity<?> listMessages(@RequestBody Map<String, Object> params) {
            Integer limit = (Integer) params.getOrDefault("limit", 10);

            if (limit > 20) {
                return ResponseEntity.badRequest().body(Map.of("error", "limit cannot be greater than 20"));
            }

            List<Messages> messages = messageJpaRepo.findAllByOrderByMessageIdDesc();

            return ResponseEntity.ok(messages);
        }
    }

    @RestController
    @RequestMapping("/user")
    public class UserController {

        private static final Logger log = LoggerFactory.getLogger(UserController.class);


        /**
         * Creates a new user with the provided public key.
         *
         * @param params The user details received in the request body.
         * @return A ResponseEntity with a message acknowledging the creation of the user.
         */
        @PostMapping("/create")
        public ResponseEntity<?> createUser(@RequestBody User params) {
            // Sanitize the public key by removing header, footer, and whitespace
//            String publicKey = params.getPublicKey().replace("-----BEGIN PUBLIC KEY-----", "")
//                    .replace("-----END PUBLIC KEY-----", "")
//                    .replaceAll("\\s", "");

            log.info("Creating user with username: {}", params.getUser());

            return ResponseEntity.ok(Map.of("message", "welcome"));
        }

        @GetMapping("/{username}/public-key")
        public ResponseEntity<?> getPublicKey(@PathVariable String username) {
            log.info("Retrieving public key for username: {}", username);
            String publicKey ;
//                    userService.getPublicKeyByUsername(username);

//            if (publicKey == null) {
//                log.warn("Public key not found for username: {}", username);
//                return ResponseEntity.badRequest().body(Map.of("error", "User not found."));
//            }

            return ResponseEntity.ok(Map.of("public-key", "-----BEGIN PUBLIC KEY-----\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3Bsn4p1MKY02l8499qRz\nMg980BXswaduhnCBRTn6PBvEI2ywIzhOiQjq98HGiXWqbcNYibWmGuwUvhpDLzfJ\nOxtgRO9I612rd4xzs8tz+2pYDO1bOtwuGQTTTQ1jwlCyPyxYsNJgnr8wsiQE7siW\n9WbsuEYkzUOUIF7RGv8rW0dGNYdb8QLan8ghTu4es0uI2LfzBg3usFJahS+Pcih5\nDglphAcDJBbX+EbGytGUpkdYOoJNMi+AVPDjd8M9eujDjER1YxgvOMSK0GbzkDIW\nkzmU9SgDzUqjU5W5Q4P1eoz+QtL0m5E+uoXN8fuY5rw4cr4YE0srACsG30j41PfQ\nTQIDAQAB\n-----END PUBLIC KEY-----\n"));
        }
    }
}

package edu.sjsu.cmpe272.simplemicroblog.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for handling user-related requests.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserManagementService userManagementService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User userData) {
        String cleanPublicKey = userData.getPublicKey()
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        logger.info("Creating user with username: {}", userData.getUser());
        userManagementService.addUser(userData.getUser(), cleanPublicKey);

        return ResponseEntity.ok(Map.of("message", "User successfully created."));
    }


    @GetMapping("/{username}/public-key")
    public ResponseEntity<?> fetchPublicKey(@PathVariable String username) {
        logger.info("Retrieving public key for username: {}", username);
        String userPublicKey = userManagementService.fetchPublicKeyByUsername(username);

        if (userPublicKey == null) {
            logger.warn("Public key not found for username: {}", username);
            return ResponseEntity.badRequest().body(Map.of("error", "User not found."));
        }

        return ResponseEntity.ok(Map.of("public-key", userPublicKey));
    }
}

/**
 * Service for managing users and their public keys.
 */
@Service
public class UserManagementService {
    private static final Logger logger = LoggerFactory.getLogger(UserManagementService.class);

    public static Map<String, String> registeredUserPublicKeys = new HashMap<>();

    public void addUser(String username, String publicKey) {
        registeredUserPublicKeys.put(username.toLowerCase(), publicKey);
        logger.info("User added with username: {}", username);
    }

    public String fetchPublicKeyByUsername(String username) {
        String publicKey = registeredUserPublicKeys.get(username.toLowerCase());
        if (publicKey == null) {
            logger.warn("Public key not found for username: {}", username);
            return null;
        } else {
            logger.debug("Public key retrieved for username: {}", username);
        }
        return publicKey;
    }

    public static PublicKey convertStringToPublicKey(String keyStr) throws Exception {
        try {
            byte[] byteKey = Base64.getDecoder().decode(keyStr.getBytes());
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {
            logger.error("Error converting string to public key: {}", e.getMessage());
            return null;
        }
    }

    public static PrivateKey convertStringToPrivateKey(String keyStr) throws Exception {
        byte[] byteKey = Base64.getDecoder().decode(keyStr.getBytes());
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(byteKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePrivate(privateKeySpec);
    }
}

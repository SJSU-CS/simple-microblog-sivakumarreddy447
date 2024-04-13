package edu.sjsu.cmpe272.simpleblog.server;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a user in the system, including their username and public key.
 */
public class User {
    // Logger instance for the User class.
    private static final Logger log = LoggerFactory.getLogger(User.class);

    @Getter
    @Setter
    private String user;
    @Getter
    @Setter
    private String publicKey;

    public User() {
    }

    public User(String user, String publicKey) {
        this.user = user;
        this.publicKey = publicKey;
    }


}

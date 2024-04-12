package edu.sjsu.cmpe272.simpleblog.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a user in the system, including their username and public key.
 */
public class User {
    // Logger instance for the User class.
    private static final Logger log = LoggerFactory.getLogger(User.class);

    private String user;
    private String publicKey;

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Constructs a new User with specified username and public key.
     *
     * @param user      The username of the user.
     * @param publicKey The public key associated with the user.
     */
    public User(String user, String publicKey) {
        this.user = user;
        this.publicKey = publicKey;
    }

    /**
     * Sets the username of the user.
     *
     * @param user The new username.
     */
    public void setUser(String user) {
        // Log the change of username if necessary. This could be considered sensitive or verbose.
        log.debug("Updating username to {}", user);
        this.user = user;
    }

    /**
     * Sets the public key of the user.
     *
     * @param publicKey The new public key.
     */
    public void setPublicKey(String publicKey) {
        // Log the update of the public key if necessary. Consider the sensitivity of this information.
        log.debug("Updating public key for user {}", user);
        this.publicKey = publicKey;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return The username.
     */
    public String getUser() {
        return user;
    }

    /**
     * Retrieves the public key of the user.
     *
     * @return The public key.
     */
    public String getPublicKey() {
        return publicKey;
    }
}

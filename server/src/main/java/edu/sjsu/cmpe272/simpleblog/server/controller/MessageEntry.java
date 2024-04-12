package edu.sjsu.cmpe272.simpleblog.server;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor // Generates the no-arg constructor
public class MessageEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, length = 1000) // Assuming message length can be up to 1000 characters
    private String messageContent; // Renamed from 'message' to 'messageContent' for clarity

    @Column(nullable = true)
    private String attachment;

    @Column(nullable = false, length = 2048)
    private String signature;

    /**
     * Constructs a new MessageEntry with all fields.
     *
     * @param date The date of the message entry.
     * @param author The author of the message.
     * @param messageContent The content of the message.
     * @param attachment Any attachment as a string. Can be null.
     * @param signature The cryptographic signature for message authenticity.
     */
    public MessageEntry(String date, String author, String messageContent, String attachment, String signature) {
        this.date = date;
        this.author = author;
        this.messageContent = messageContent;
        this.attachment = attachment;
        this.signature = signature;
    }

    // Optional wrapper for nullable field
    public Optional<String> getAttachment() {
        return Optional.ofNullable(attachment);
    }
}

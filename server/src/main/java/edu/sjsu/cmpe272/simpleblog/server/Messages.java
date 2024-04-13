package edu.sjsu.cmpe272.simpleblog.server;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Entity
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long messageId;

    @Getter
    @Setter
    @Column(nullable = false)
    private String date;

    @Getter
    @Setter
    @Column(nullable = false)
    private String author;

    @Getter
    @Setter
    @Column(nullable = false, length = 1000) // Assuming message length can be up to 1000 characters
    private String message;

    @Getter
    @Setter
    @Column(nullable = true)
    private String attachment;

    @Getter
    @Setter
    @Column(nullable = false,length = 2048)
    private String signature;
    public Messages(){

    }

    // Constructor with all fields
    public Messages(String date, String author, String message, String attachment, String signature) {
        this.date = date;
        this.author = author;
        this.message = message;
        this.attachment = attachment;
        this.signature = signature;
    }
}

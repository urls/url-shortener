package me.amarpandey.urlshortener.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Document(collection = "url")
public class Url {

    @Id
    private String id;
    private final String longUrl;

    @Indexed(unique = true)
    private String shorthand;

    private LocalDateTime localDateTime;

    public Url(String longUrl, String shorthand, LocalDateTime localDateTime) {
        this.longUrl = longUrl;
        this.shorthand = shorthand;
        this.localDateTime = localDateTime;
    }
}

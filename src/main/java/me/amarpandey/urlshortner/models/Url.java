package me.amarpandey.urlshortner.models;

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
    private String url;

    @Indexed(unique = true)
    private String shorthand;

    private LocalDateTime localDateTime;

    public Url(String url, String shorthand, LocalDateTime localDateTime) {
        this.url = url;
        this.shorthand = shorthand;
        this.localDateTime = localDateTime;
    }
}

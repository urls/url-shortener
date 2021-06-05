package me.amarpandey.urlshortener.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class Response {
    private String message;
    private UrlShortenResponse shortenResponse;

    public Response(String message) {
        this.message = message;
        this.shortenResponse = null;
    }

    public Response(UrlShortenResponse shortenResponse) {
        this.message = null;
        this.shortenResponse = shortenResponse;
    }
}

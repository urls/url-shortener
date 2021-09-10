package me.amarpandey.urlshortener.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class Response {
    private String url;
    private String message;
    private String shortenCode;

    public Response(String message) {
        this.url = null;
        this.message = message;
        this.shortenCode = null;
    }

    public Response(String url, String shortenCode) {
        this.url = url;
        this.message = null;
        this.shortenCode = shortenCode;
    }
}

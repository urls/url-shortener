package me.amarpandey.urlshortener.controller;


import io.vavr.control.Either;
import me.amarpandey.urlshortener.controller.response.Response;
import me.amarpandey.urlshortener.services.UrlShortenerService;
import me.amarpandey.urlshortener.validators.UrlValidatorUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class UrlShortenerController {

    public final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }


    @GetMapping(value = "/{shorten_code}")
    public void findUrlForGivenShortenCode(
            @PathVariable(required = true, value = "shorten_code") final String shortenCode,
            HttpServletResponse response) throws IOException {
        String result = this.urlShortenerService.findUrlForGivenShortenCode(shortenCode);
        response.sendRedirect(UrlValidatorUtil.isValidUrl(result) ? result : "/");
    }


    @PostMapping(value = "/shorten", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> returnShortenCode(@RequestBody final String url) {
        Either<String, String> either = this.urlShortenerService.returnShortenCode(url);

        if (either.isLeft()) {
            return new ResponseEntity<>(new Response(either.getLeft()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new Response(url, either.get()), HttpStatus.OK);
    }
}

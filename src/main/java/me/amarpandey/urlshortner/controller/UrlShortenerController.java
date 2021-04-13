package me.amarpandey.urlshortner.controller;


import me.amarpandey.urlshortner.services.UrlShortenerService;
import me.amarpandey.urlshortner.validators.UrlValidator;
import org.springframework.http.HttpStatus;
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

    /**
     * Post mapping to accept the long URL and return the shorten code mapped to it.
     *
     * @param url Long URL that is to be shortened.
     * @return Return the {@link ResponseEntity} with shorten and HTTP status code.
     */
    @PostMapping(value = "/shorten")
    public ResponseEntity<String> returnShortenCode(@RequestBody final String url) {
        return new ResponseEntity<>(this.urlShortenerService.returnShortenCode(url), HttpStatus.OK);
    }

    /**
     * Get mapping to send the redirect to the long URL for the given shorten code if valid, else throw {@link IOException}.
     *
     * @param shortenCode for which the long URL is to be fetched and returned.
     * @param response    {@link HttpServletResponse} to url redirect.
     * @throws IOException
     */
    @GetMapping(value = "/{SHORTEN_CODE}")
    public void findUrlForGivenShortenCode(@PathVariable("SHORTEN_CODE") final String shortenCode, HttpServletResponse response) throws IOException {
        String result = this.urlShortenerService.findUrlForGivenShortenCode(shortenCode);
        response.sendRedirect(UrlValidator.isValidUrl(result) ? result : "/");
    }
}

package me.amarpandey.urlshortner.services;

import lombok.NonNull;
import me.amarpandey.urlshortner.models.Url;
import me.amarpandey.urlshortner.models.strategy.ShortenCodeStrategy;
import me.amarpandey.urlshortner.repository.UrlShortenerRepository;
import me.amarpandey.urlshortner.validators.UrlValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static me.amarpandey.urlshortner.utils.Constants.INVALID_SHORTHAND_CODE;
import static me.amarpandey.urlshortner.utils.Constants.INVALID_URL;

@Component
public class UrlShortenerService {

    public final ShortenCodeStrategy shortenCodeStrategy;
    public final UrlShortenerRepository urlShortenerRepository;

    public UrlShortenerService(UrlShortenerRepository urlRepository, ShortenCodeStrategy shortenCodeStrategy) {
        this.urlShortenerRepository = urlRepository;
        this.shortenCodeStrategy = shortenCodeStrategy;
    }

    /**
     * Check of the given URL is valid, if valid check if it is already shortened,
     * if not shorten and return the shorten code.
     *
     * @param longURL URL that needed to be shortened.
     * @return String representing Shorten code.
     */
    public String returnShortenCode(@NonNull final String longURL) {
        if (!UrlValidator.isValidUrl(longURL)) return INVALID_URL;
        return isUrlAlreadyShortened(longURL) ?
                this.urlShortenerRepository.findByUrl(longURL).getShorthand() :
                createShortenUrl(longURL);
    }

    /**
     * Check if the given shorten code is not null and if valid return the URL back to the user.
     *
     * @param shortenCode Shorten code to which the long URL is mapped.
     * @return Long URL which is mapped to this shorten code.
     */
    public String findUrlForGivenShortenCode(@NonNull final String shortenCode) {
        Url url = this.urlShortenerRepository.findByShorthand(shortenCode);
        if (url == null) return INVALID_SHORTHAND_CODE;
        return url.getUrl();
    }

    /**
     * Create Short hand code for the given Long URL. If any exception occurs while inserting return the message back.
     *
     * @param longURL String containing the Long URL.
     * @return String Shorten code.
     */
    private String createShortenUrl(final String longURL) {
        String shortenCode = shortenCodeStrategy.generateCode();
        Url url = new Url(longURL, shortenCode, LocalDateTime.now());
        try {
            Url insertedUrl = urlShortenerRepository.insert(url);
            return insertedUrl.getShorthand();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Helper method to validate whether the URL had been shortened before or not by system.
     *
     * @param longURL String Long URL
     * @return Boolean indicating whether URL is shortened before or not.
     */
    private boolean isUrlAlreadyShortened(final String longURL) {
        Url url = this.urlShortenerRepository.findByUrl(longURL);
        if (url != null) {
            return true;
        }
        return false;
    }
}

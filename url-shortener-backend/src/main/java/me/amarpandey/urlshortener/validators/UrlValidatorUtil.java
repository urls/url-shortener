package me.amarpandey.urlshortener.validators;

import org.apache.commons.validator.routines.UrlValidator;

public class UrlValidatorUtil {

    public static boolean isValidUrl(String url) {
        try {
            if (urlValidator(url)) {
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e){
            return false;
        }
    }

    public static boolean urlValidator(String url)    {
        UrlValidator defaultValidator = new UrlValidator();
        return defaultValidator.isValid(url);
    }

}


package me.amarpandey.urlshortener.validators;

import org.apache.commons.validator.routines.UrlValidator;

public class UrlValidatorUtil {

    public static boolean isValid(String url) {
        try {
            UrlValidator defaultValidator = new UrlValidator();
            return defaultValidator.isValid(url);
        }
        catch (Exception e){
            return false;
        }
    }

}


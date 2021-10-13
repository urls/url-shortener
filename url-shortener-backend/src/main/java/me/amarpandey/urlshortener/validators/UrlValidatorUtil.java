package me.amarpandey.urlshortener.validators;

import org.apache.commons.validator.routines.UrlValidator;

public class UrlValidatorUtil {

    public static boolean isValid(String url) {
        try {
            UrlValidator defaultValidator = new UrlValidator();
            if (defaultValidator.isValid(url)) {
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e){
            return false;
        }
    }

}


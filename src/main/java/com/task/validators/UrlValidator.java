package com.task.validators;
import com.task.dtos.exception.UrlException;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlValidator {

    public static void validate(String url) throws UrlException {
        try {
            URL obj = new URL(url);
            obj.toURI();
        } catch (MalformedURLException | URISyntaxException e) {
           throw new UrlException(String.format("Url %s is not valid", url));
        }
    }
}

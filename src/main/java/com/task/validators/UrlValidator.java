package com.task.validators;
import com.task.dtos.exception.UrlException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class UrlValidator {

    @Value("${short-url.prefix}")
    private String prefix;

    public void validate(String url) throws UrlException {
        if (!url.startsWith("http")) {
            throw new UrlException(String.format("Url %s is not valid. It must start with 'http' or 'https'", url));
        }
        if (url.contains(prefix)) {
            throw new UrlException(String.format("Url %s is already shortened", url));
        }
        try {
            URL obj = new URL(url);
            obj.toURI();
        } catch (MalformedURLException | URISyntaxException e) {
           throw new UrlException(String.format("Url %s is not valid", url));
        }
    }
}

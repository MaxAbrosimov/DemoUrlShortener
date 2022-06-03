package com.task.services;

import com.task.converters.UrlConverter;
import com.task.dtos.ShortifyDto;
import com.task.dtos.UrlNotFoundException;
import com.task.utils.UrlShortener;
import com.task.domain.ShortToUrl;
import com.task.domain.UrlToShort;
import com.task.dtos.UrlException;
import com.task.repositories.ShortToUrlRepository;
import com.task.repositories.UrlToShortRepository;
import com.task.validators.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UrlService {

    private final UrlToShortRepository urlToShortRepository;
    private final ShortToUrlRepository shortToUrlRepository;
    private final UrlConverter urlConverter;

    @Autowired
    public UrlService(
            UrlToShortRepository urlToShortRepository,
            ShortToUrlRepository shortToUrlRepository,
            UrlConverter urlConverter
    ) {
        this.urlToShortRepository = urlToShortRepository;
        this.shortToUrlRepository = shortToUrlRepository;
        this.urlConverter = urlConverter;
    }

    public List<ShortifyDto> listAll() {
        List<ShortifyDto> list = new ArrayList<>();
        urlToShortRepository.findAll().forEach(url -> list.add(urlConverter.convert(url)));
        return list;
    }

    public ShortifyDto getByShort(String shortUrl) throws UrlNotFoundException {
        return urlConverter.convert(shortToUrlRepository.findById(shortUrl).orElseThrow(UrlNotFoundException::new));
    }

    public ShortifyDto shortifyUrl(String url) throws UrlException {
        String urlTo = (url.contains("http://") || url.contains("https://")) ? url : "https://" + url;
        UrlValidator.validate(urlTo);
        Optional<UrlToShort> shortUrlOpt = urlToShortRepository.findById(urlTo);
        if (!shortUrlOpt.isPresent()) {
            String shortUrl = UrlShortener.shortifyUrl(urlTo);

            UrlToShort urlToShortUrl = new UrlToShort();

            urlToShortUrl.setShortUrl(shortUrl);
            urlToShortUrl.setUrl(urlTo);
            urlToShortRepository.save(urlToShortUrl);

            ShortToUrl shortUrlToUrl = new ShortToUrl();
            shortUrlToUrl.setShortUrl(shortUrl);
            shortUrlToUrl.setUrl(urlTo);
            shortToUrlRepository.save(shortUrlToUrl);
            return urlConverter.convert(urlTo, shortUrl);
        } else {
            return urlConverter.convert(shortUrlOpt.get().getUrl(), shortUrlOpt.get().getShortUrl());
        }
    }
}

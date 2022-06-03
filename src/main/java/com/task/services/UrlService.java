package com.task.services;

import com.datastax.driver.core.utils.UUIDs;
import com.task.converters.UrlConverter;
import com.task.dtos.ShortifyDto;
import com.task.dtos.exception.UrlNotFoundException;
import com.task.domain.ShortToUrl;
import com.task.domain.UrlToShort;
import com.task.dtos.exception.UrlException;
import com.task.repositories.ShortToUrlRepository;
import com.task.repositories.UrlToShortRepository;
import com.task.validators.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlService {

    private final UrlToShortRepository urlToShortRepository;
    private final ShortToUrlRepository shortToUrlRepository;
    private final UrlConverter urlConverter;
    private final UrlValidator urlValidator;

    @Autowired
    public UrlService(
            UrlToShortRepository urlToShortRepository,
            ShortToUrlRepository shortToUrlRepository,
            UrlConverter urlConverter,
            UrlValidator urlValidator
    ) {
        this.urlToShortRepository = urlToShortRepository;
        this.shortToUrlRepository = shortToUrlRepository;
        this.urlConverter = urlConverter;
        this.urlValidator = urlValidator;
    }

    public List<ShortifyDto> listAll() {
        List<ShortifyDto> list = new ArrayList<>();
        urlToShortRepository.findAll().forEach(url -> list.add(urlConverter.convert(url)));
        return list;
    }

    public ShortifyDto getByShort(String shortUrl) throws UrlNotFoundException {
        return urlConverter.convert(shortToUrlRepository.findById(Long.parseLong(shortUrl)).orElseThrow(UrlNotFoundException::new));
    }

    //TODO
    // add logs, code cleanup
    public ShortifyDto shortifyUrl(String url) throws UrlException {
        urlValidator.validate(url);
        Optional<UrlToShort> shortUrlOpt = urlToShortRepository.findById(url);
        if (!shortUrlOpt.isPresent()) {
            ShortToUrl shortToUrlSaved = shortToUrlRepository.save(new ShortToUrl(url));

            UrlToShort urlToShortUrl = new UrlToShort();
            urlToShortUrl.setShortUrl(shortToUrlSaved.getShortUrl());
            urlToShortUrl.setUrl(url);
            urlToShortRepository.save(urlToShortUrl);

            return urlConverter.convert(shortToUrlSaved);
        } else {
            return urlConverter.convert(shortUrlOpt.get().getUrl(), shortUrlOpt.get().getShortUrl());
        }
    }
}

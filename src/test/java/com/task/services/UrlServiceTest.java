package com.task.services;

import com.task.converters.UrlConverter;
import com.task.domain.ShortToUrl;
import com.task.domain.UrlToShort;
import com.task.dtos.ShortifyDto;
import com.task.dtos.exception.UrlNotFoundException;
import com.task.repositories.ShortToUrlRepository;
import com.task.repositories.UrlToShortRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceTest {

    private static final Long SHORT_URL = 1L;
    private static final String LONG_URL = "Some long url";

    @Mock
    private UrlToShortRepository urlToShortRepository;
    @Mock
    private ShortToUrlRepository shortToUrlRepository;
    @Mock
    private UrlConverter urlConverter;

    @InjectMocks
    private UrlService urlService;

    @Test
    public void testListAll() {
        //given
        UrlToShort urlToShort = new UrlToShort();
        ShortifyDto shortifyDto = new ShortifyDto();

        //when
        when(urlToShortRepository.findAll()).thenReturn(Collections.singletonList(urlToShort));
        when(urlConverter.convert(urlToShort)).thenReturn(shortifyDto);
        //then

        List<ShortifyDto> result = urlService.listAll();
        Assert.assertEquals(result, Collections.singletonList(shortifyDto));
        verify(urlToShortRepository).findAll();
        verify(urlConverter).convert(urlToShort);
    }

    @Test
    public void testGetByShort() throws UrlNotFoundException {
        //given
        ShortToUrl shortToUrl = new ShortToUrl(LONG_URL);
        shortToUrl.setShortUrl(SHORT_URL);
        ShortifyDto shortifyDto = new ShortifyDto();

        //when
        when(shortToUrlRepository.findById(SHORT_URL)).thenReturn(Optional.of(shortToUrl));
        when(urlConverter.convert(shortToUrl)).thenReturn(shortifyDto);
        //then

        ShortifyDto result = urlService.getByShort(SHORT_URL.toString());
        Assert.assertEquals(result, shortifyDto);
        verify(shortToUrlRepository).findById(SHORT_URL);
        verify(urlConverter).convert(shortToUrl);
    }

    @Test(expected = UrlNotFoundException.class)
    public void testGetByShortThrowsException() throws UrlNotFoundException {
        //when
        when(shortToUrlRepository.findById(SHORT_URL)).thenReturn(Optional.empty());
        //then

        urlService.getByShort(SHORT_URL.toString());

        verify(shortToUrlRepository).findById(SHORT_URL);
        verifyZeroInteractions(urlConverter);
    }

}

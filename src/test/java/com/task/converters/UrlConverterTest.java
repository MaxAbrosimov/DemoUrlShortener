package com.task.converters;

import com.task.domain.ShortToUrl;
import com.task.domain.UrlToShort;
import com.task.dtos.ShortifyDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class UrlConverterTest {

    private static final String PREFIX = "https://shortapp/r/";
    private static final String LONG_URL = "https://some_long.url";
    private static final Long SHORT_URL = 1L;

    @InjectMocks
    private UrlConverter urlConverter;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(urlConverter, "prefix", PREFIX);
    }

    @Test
    public void testConvert2Params() {
        ShortifyDto result = urlConverter.convert(LONG_URL, SHORT_URL);

        Assert.assertEquals(result.getLongUrl(), LONG_URL);
        Assert.assertEquals(result.getShortUrl(), concatUrlWithPref(SHORT_URL.toString()));
    }

    @Test
    public void testConvertShortToUrl() {
        //given
        ShortToUrl shortToUrl = new ShortToUrl(LONG_URL);
        //then
        ShortifyDto result = urlConverter.convert(shortToUrl);

        Assert.assertEquals(result.getLongUrl(), shortToUrl.getUrl());
    }

    @Test
    public void testConvertUrlToShort() {
        //given
        UrlToShort urlToShort = new UrlToShort();
        urlToShort.setShortUrl(SHORT_URL);
        urlToShort.setUrl(LONG_URL);
        //then
        ShortifyDto result = urlConverter.convert(urlToShort);

        Assert.assertEquals(result.getLongUrl(), urlToShort.getUrl());
        Assert.assertEquals(result.getShortUrl(), concatUrlWithPref(urlToShort.getShortUrl().toString()));
    }

    private String concatUrlWithPref(String url) {
        return PREFIX + url;
    }

}

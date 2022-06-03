package com.task.controllers;

import com.task.dtos.ShortifyDto;
import com.task.dtos.exception.UrlException;
import com.task.dtos.exception.UrlNotFoundException;
import com.task.services.UrlService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlControllerTest {

    private static final String URL = "https://some-long.url";
    private static final String SHORT_URL = "https://short.url";

    @Mock
    private UrlService urlService;

    @InjectMocks
    private UrlController urlController;

    @Test
    public void testList() {
        //given
        List<ShortifyDto> expectedList = new ArrayList<>();

        //when
        when(urlService.listAll()).thenReturn(expectedList);

        //then
        ResponseEntity<List<ShortifyDto>> result = urlController.list();

        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(result.getBody(), expectedList);
        verify(urlService).listAll();
    }

    @Test
    public void testTransformToShort() throws UrlException {
        //given
        ShortifyDto request = new ShortifyDto();
        request.setLongUrl(URL);
        ShortifyDto response = new ShortifyDto();
        request.setLongUrl(URL);
        request.setShortUrl(SHORT_URL);

        //when
        when(urlService.shortifyUrl(request.getLongUrl())).thenReturn(response);

        //then
        ResponseEntity<ShortifyDto> result = urlController.transformToShort(request);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(result.getBody(), response);
        verify(urlService).shortifyUrl(request.getLongUrl());
    }


    @Test
    public void testGetByShort() throws UrlNotFoundException {
        //given
        ShortifyDto request = new ShortifyDto();
        request.setShortUrl(SHORT_URL);
        ShortifyDto response = new ShortifyDto();
        request.setLongUrl(URL);
        request.setShortUrl(SHORT_URL);

        //when
        when(urlService.getByShort(request.getShortUrl())).thenReturn(response);

        //then
        ResponseEntity<ShortifyDto> result = urlController.getByShort(request);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(result.getBody(), response);
        verify(urlService).getByShort(request.getShortUrl());
    }

    @Test
    public void testHandleUrlException() {
        //given
        String exceptionMessage = "some error message";
        UrlException exception = new UrlException(exceptionMessage);
        WebRequest request = mock(WebRequest.class);
        //then

        ResponseEntity<Object> result = urlController.handleUrlException(exception, request);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
        Map<String, Object> body = (Map<String, Object>) result.getBody();
        Assert.assertTrue(body.containsKey("timestamp"));
        Assert.assertTrue(body.containsKey("message"));
        String bodyMessage = (String) body.get("message");
        Assert.assertEquals(bodyMessage, exceptionMessage);
    }

    @Test
    public void testHandleUrlNotFoundException() {
        //given
        UrlNotFoundException exception = new UrlNotFoundException();
        WebRequest request = mock(WebRequest.class);
        //then

        ResponseEntity<Object> result = urlController.handleUrlNotException(exception, request);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
        Map<String, Object> body = (Map<String, Object>) result.getBody();
        Assert.assertTrue(body.containsKey("timestamp"));
    }

}

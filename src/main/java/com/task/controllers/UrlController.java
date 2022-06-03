package com.task.controllers;

import com.task.dtos.ShortifyDto;
import com.task.dtos.exception.UrlException;
import com.task.dtos.exception.UrlNotFoundException;
import com.task.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/url")
public class UrlController {

    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ShortifyDto>> list(){
        return ResponseEntity.ok(urlService.listAll());
    }

    @PostMapping(value = "/shortifyUrl")
    public ResponseEntity<ShortifyDto> transformToShort(@RequestBody ShortifyDto shortifyDto) throws UrlException {
        return ResponseEntity.ok(urlService.shortifyUrl(shortifyDto.getLongUrl()));
    }

    @PostMapping("/getByShort")
    public ResponseEntity<ShortifyDto> getByShort(@RequestBody ShortifyDto shortifyDto) throws UrlNotFoundException {
        return ResponseEntity.ok(urlService.getByShort(shortifyDto.getShortUrl()));
    }

    @ExceptionHandler(UrlException.class)
    public ResponseEntity<Object> handleUrlException(UrlException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<Object> handleUrlNotException(UrlNotFoundException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}

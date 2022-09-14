package com.javacrawler.searchch.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javacrawler.searchch.service.SearchChService;

@Slf4j
@RestController
@RequestMapping("/crawl")
@RequiredArgsConstructor

public class SearchChController {
    private final SearchChService searchChService;

    @RequestMapping("/searcch")
    public ResponseEntity<Integer> chServiceCrawlerController() {
        log.info("searcch crawler begins");

        return ResponseEntity
                .ok()
                .body(searchChService.chServiceCrawler());
    }

}

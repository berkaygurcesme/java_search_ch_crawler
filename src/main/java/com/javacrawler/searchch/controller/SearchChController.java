package com.javacrawler.searchch.controller;

import com.javacrawler.searchch.service.JsoupSearchChService;
import com.javacrawler.searchch.service.SearchChService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/crawl")
@RequiredArgsConstructor
public class SearchChController {

  private final SearchChService searchChService;
  private final JsoupSearchChService jsoupSearchChService;

  @RequestMapping("/searcch")
  public ResponseEntity<Long> chServiceCrawlerController() {
    log.info("searcch crawler begins");

    return ResponseEntity.ok().body(searchChService.chServiceCrawler());
  }

  @RequestMapping("/jsoupsearcch")
  public ResponseEntity<Long> jsoupChServiceCrawlerController() {
    log.info("jsoup searcch crawler begins");

    return ResponseEntity
      .ok()
      .body(jsoupSearchChService.jsoupChServiceCrawler());
  }
}

package com.club.tech.NewsGroupService.controller;

import com.club.tech.NewsGroupService.model.Article;
import com.club.tech.NewsGroupService.service.NewsDataTransformerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class IndexController {
  private final Logger log = LoggerFactory.getLogger(IndexController.class);
  private final NewsDataTransformerImpl newsDataTransformerImpl;

  public IndexController(NewsDataTransformerImpl newsDataTransformerImpl) {
    this.newsDataTransformerImpl = newsDataTransformerImpl;
  }

  @GetMapping("/v1/newsArticles/query")
  public ResponseEntity<List<Article>> getNewsArticlesByIntervals(
      @RequestParam Optional<String> searchKey,
      @RequestParam(required = false) Optional<Integer> digit,
      @RequestParam(required = false) Optional<String> interval) {

    log.info("@@  getNewsArticlesByInterval API WAS INVOKED");
    return newsDataTransformerImpl.groupArticlesByInterval(searchKey, digit, interval);
  }
}

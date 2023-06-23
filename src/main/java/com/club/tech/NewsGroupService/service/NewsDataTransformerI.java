package com.club.tech.NewsGroupService.service;

import com.club.tech.NewsGroupService.model.Article;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

public interface NewsDataTransformerI {

  ResponseEntity<List<Article>> groupArticlesByInterval(
      Optional<String> searchKey, Optional<Integer> n, Optional<String> interval);
}

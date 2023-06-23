package com.club.tech.NewsGroupService.service;

import com.club.tech.NewsGroupService.client.NewsFetcherService;
import com.club.tech.NewsGroupService.model.Article;
import com.club.tech.NewsGroupService.model.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsDataTransformerImpl implements NewsDataTransformerI {

  private final Logger log = LoggerFactory.getLogger(NewsDataTransformerImpl.class);
  private final NewsFetcherService newsFetcherService;
  private Optional<String> currentDateTime = Optional.empty();

  public NewsDataTransformerImpl(NewsFetcherService newsFetcherService) {
    this.newsFetcherService = newsFetcherService;
  }

  @Override
  public ResponseEntity<List<Article>> groupArticlesByInterval(
      Optional<String> searchKey, Optional<Integer> n, Optional<String> interval) {

    List<Article> filteredArticles = new LinkedList<>();
    currentDateTime =
        Optional.of(
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

    try {

      News news = newsFetcherService.fetchNewsInfo(searchKey);

      if (n.isEmpty() || interval.isEmpty()) {
        filteredArticles = filterArticles(news, 12, "hours");
      } else {
        filteredArticles = filterArticles(news, n.get(), interval.get());
      }

    } catch (Exception e) {
    }

    return ResponseEntity.ok(filteredArticles);
  }

  private List<Article> filterArticles(News news, int n, String interval) {
    List<Article> filteredArticles =
        news.getArticles().stream()
            .filter(
                article ->
                    compareDateTime(
                            LocalDateTime.parse(article.getPublishedAt().replace("Z", "")),
                            calculateStartDateTime(
                                LocalDateTime.parse(currentDateTime.get()), n, interval))
                        > 0)
            .collect(Collectors.toList());

    log.info(
        "%s%s"
            .formatted(
                "@@ FILTERED LAST %d %s ARTICLES: ".formatted(n, interval), filteredArticles));
    return filteredArticles;
  }

  private int compareDateTime(LocalDateTime publishedDateTime, LocalDateTime startDateTime) {
    log.debug(
        "@@ COMPARING publishedDateTime %s WITH startDateTime %s"
            .formatted(publishedDateTime.toString(), startDateTime.toString()));
    return publishedDateTime.compareTo(startDateTime);
  }

  private LocalDateTime calculateStartDateTime(
      LocalDateTime currentDateTime, int n, String interval) {

    return currentDateTime.minus(n, getChronoUnit(interval));
  }

  private ChronoUnit getChronoUnit(String interval) {
    return switch (interval) {
      case "minutes" -> ChronoUnit.MINUTES;
      case "hours" -> ChronoUnit.HOURS;
      case "days" -> ChronoUnit.DAYS;
      case "weeks" -> ChronoUnit.WEEKS;
      case "months" -> ChronoUnit.MONTHS;
      case "years" -> ChronoUnit.YEARS;
      default -> throw new IllegalArgumentException("Argument supplied is not supported !");
    };
  }
}

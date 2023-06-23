package com.club.tech.NewsGroupService.client;

import com.club.tech.NewsGroupService.model.News;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@Service
public class NewsFetcherService {

  private String NEWS_API_URL = "";

  private RestTemplate client;

  public NewsFetcherService(RestTemplate restTemplate) {
    this.client = restTemplate;
  }

  public News fetchNewsInfo(Optional<String> searchKey) {
    News news = null;
    try {
      news = client.getForObject(NEWS_API_URL.formatted(searchKey.get()), News.class);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return news;
  }
}

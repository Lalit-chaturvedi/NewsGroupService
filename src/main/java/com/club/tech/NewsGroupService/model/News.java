package com.club.tech.NewsGroupService.model;

import java.util.List;

public class News {

  private String status;
  private int totalResults;
  private List<Article> articles;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getTotalResults() {
    return totalResults;
  }

  public void setTotalResults(int totalResults) {
    this.totalResults = totalResults;
  }

  public List<Article> getArticles() {
    return articles;
  }

  public void setArticles(List<Article> articles) {
    this.articles = articles;
  }

  @Override
  public String toString() {
    return "News{"
        + "status='"
        + status
        + '\''
        + ", totalResults="
        + totalResults
        + ", articles="
        + articles
        + '}';
  }
}

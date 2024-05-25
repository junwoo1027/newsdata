package sample.newsdata.api.service.article;

import sample.newsdata.domain.article.Article;

import java.util.List;

public interface ArticleScraper {

    List<Article> fetchArticles(String keyword);

}
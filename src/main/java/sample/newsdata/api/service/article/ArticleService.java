package sample.newsdata.api.service.article;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.newsdata.api.controller.article.request.CreateArticleRequest;
import sample.newsdata.domain.article.Article;
import sample.newsdata.domain.article.ArticleRepository;
import sample.newsdata.domain.article.ArticleSource;
import sample.newsdata.domain.article.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final NaverArticleScraper naverArticleScraper;
    private final DaumArticleScraper daumArticleScraper;
    private final ArticleRepository articleRepository;

    @Transactional
    public List<ArticleResponse> createNews(CreateArticleRequest request) {
        List<Article> getArticles = this.fetchArticles(request.keyword(), request.articleSource());
        List<Article> articles = articleRepository.saveAll(getArticles);
        return articles.stream()
                .map(ArticleResponse::of)
                .collect(Collectors.toList());
    }

    private List<Article> fetchArticles(String keyword, ArticleSource source) {
        List<Article> articles = new ArrayList<>();
        switch (source) {
            case NAVER:
                articles.addAll(naverArticleScraper.fetchArticles(keyword));
                break;
            case DAUM:
                articles.addAll(daumArticleScraper.fetchArticles(keyword));
                break;
            case ALL:
                articles.addAll(naverArticleScraper.fetchArticles(keyword));
                articles.addAll(daumArticleScraper.fetchArticles(keyword));
                break;
        }
        return articles;
    }

}

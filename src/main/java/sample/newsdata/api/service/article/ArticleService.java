package sample.newsdata.api.service.article;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.newsdata.domain.article.Article;
import sample.newsdata.domain.article.ArticleSource;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final NaverArticleScraper naverArticleScraper;
    private final DaumArticleScraper daumArticleScraper;

    @Transactional
    public void createNews(String keyword, ArticleSource source) {
        if (source.equals(ArticleSource.NAVER)) {
            List<Article> articles = naverArticleScraper.fetchArticles(keyword);
        } else if (source.equals(ArticleSource.DAUM)) {
            List<Article> articles = daumArticleScraper.fetchArticles(keyword);
        } else if (source.equals(ArticleSource.ALL)) {
            List<Article> articles = naverArticleScraper.fetchArticles(keyword);
            List<Article> articles1 = daumArticleScraper.fetchArticles(keyword);
        } else {
            throw new IllegalArgumentException("Unsupported crawler source: " + source);
        }
    }

}

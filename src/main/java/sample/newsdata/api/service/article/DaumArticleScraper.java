package sample.newsdata.api.service.article;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import sample.newsdata.domain.article.Article;
import sample.newsdata.domain.article.ArticleRepository;
import sample.newsdata.domain.article.ArticleSource;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DaumArticleScraper implements ArticleScraper {

    private static final String BASE_URL = "https://search.daum.net/search";
    private static final int PAGE_SIZE = 10;
    private static final int TOTAL_PAGES = 10;
    private final ArticleRepository articleRepository;
    private List<Article> articleList = new ArrayList<>();

    @Transactional
    @Override
    public List<Article> fetchArticles(String keyword) {
        for (int i = 1; i <= TOTAL_PAGES; i++) {
            try {
                String url = this.generateUrl(keyword, i);
                Document document = this.fetchDocument(url);
                this.processArticles(document, keyword);
            } catch (IOException e) {
                log.error("Error fetching document for URL at page {}: {}", i, e.getMessage());
            }
        }
        List<Article> articles = this.saveArticles(this.articleList);
        return articles;
    }

    private String generateUrl(String keyword, int start) {
        return BASE_URL + "?w=news&q=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8)
                + "&sort=recency&p=" + start;
    }

    private Document fetchDocument(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
                .timeout(5000).get(); // 5초 타임아웃 설정
    }

    private void processArticles(Document document, String keyword) {

        Elements titleElements = document.select("div.item-title").select("a");
        Elements descElements = document.select("div.item-contents").select("a");

        for (int i = 0; i < PAGE_SIZE; i++) {
            if (i < titleElements.size() && i < descElements.size()) {
                String title = titleElements.get(i).text();
                String description = descElements.get(i).text();
                String link = titleElements.get(i).attr("href");

                Article article = new Article(title, description, link, keyword, ArticleSource.DAUM);
                this.articleList.add(article);
            }
        }
    }

    private List<Article> saveArticles(List<Article> articleList) {
        List<Article> articles = new ArrayList<>();
        try {
            articles = articleRepository.saveAll(articleList);
            return  articles;
        } catch (Exception e) {
            log.error("Error saving articles: {}", e.getMessage());
        }
        return articles;
    }

}

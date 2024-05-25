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
public class NaverArticleScraper implements ArticleScraper {

    private static final String BASE_URL = "https://search.naver.com/search.naver";
    private static final int PAGE_SIZE = 10;
    private static final int TOTAL_PAGES = 10;
    private final ArticleRepository articleRepository;
    private List<Article> articleList = new ArrayList<>();

    @Transactional
    @Override
    public List<Article> fetchArticles(String keyword) {
        for (int i = 0; i < TOTAL_PAGES; i++) {
            int start = i * PAGE_SIZE + 1;
            try {
                String url = this.generateUrl(keyword, start);
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
        return BASE_URL + "?where=news&query=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8)
                + "&sort=1&start=" + start;
    }

    private Document fetchDocument(String url) throws IOException {
        return Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(5000).get(); // 5초 타임아웃 설정
    }

    private void processArticles(Document document, String keyword) {
        Elements titleElements = document.select("a.news_tit");
        Elements descElements = document.select("div.news_dsc");

        for (int i = 0; i < PAGE_SIZE; i++) {
            if (i < titleElements.size() && i < descElements.size()) {
                String title = titleElements.get(i).text();
                String description = descElements.get(i).text();
                String link = titleElements.get(i).attr("href");

                Article article = new Article(title, description, link, keyword, ArticleSource.NAVER);
                this.articleList.add(article);
            }
        }
    }

    private List<Article> saveArticles(List<Article> articleList) {
        List<Article> articles = new ArrayList<>();
        try {
            articles = articleRepository.saveAll(articleList);
        } catch (Exception e) {
            log.error("Error saving articles: {}", e.getMessage());
        }
        return articles;
    }

}

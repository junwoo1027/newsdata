package sample.newsdata.api.controller.article;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.newsdata.api.service.article.ArticleService;
import sample.newsdata.domain.article.ArticleSource;

@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/api/v1/articles")
    public void createNews() {
        this.articleService.createNews("개발자", ArticleSource.ALL);
    }

}

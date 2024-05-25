package sample.newsdata.api.controller.article;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.newsdata.api.ApiResponse;
import sample.newsdata.api.controller.article.request.CreateArticleRequest;
import sample.newsdata.api.service.article.ArticleService;
import sample.newsdata.domain.article.response.ArticleResponse;
import sample.newsdata.domain.user.ApiUser;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/api/v1/articles")
    public ApiResponse<List<ArticleResponse>> getArticles(ApiUser apiUser) {
        return ApiResponse.ok(this.articleService.getArticles());
    }

    @PostMapping("/api/v1/articles")
    public ApiResponse<List<ArticleResponse>> createNews(ApiUser apiUser, @Valid @RequestBody CreateArticleRequest request) {
        return ApiResponse.ok(this.articleService.createArticle(request));
    }
    
}

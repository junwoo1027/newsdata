package sample.newsdata.api.controller.article;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sample.newsdata.api.ApiResponse;
import sample.newsdata.api.controller.article.request.CreateTargetRequest;
import sample.newsdata.api.service.article.ArticleTargetService;
import sample.newsdata.domain.article.response.ArticleTargetResponse;
import sample.newsdata.domain.user.ApiUser;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ArticleTargetController {

    private final ArticleTargetService articleTargetService;

    @GetMapping("/api/v1/targets")
    public ApiResponse<List<ArticleTargetResponse>> getArticleTargets(ApiUser apiUser) {
        return ApiResponse.ok(this.articleTargetService.getTargets());
    }

    @PostMapping("/api/v1/targets")
    public ApiResponse<ArticleTargetResponse> createArticleTarget(ApiUser apiUser, @Valid @RequestBody CreateTargetRequest request) {
        return ApiResponse.ok(this.articleTargetService.createTarget(request));
    }

    @DeleteMapping("/api/v1/targets/{targetId}")
    public ApiResponse deleteArticleTarget(ApiUser apiUser, @PathVariable("targetId") Long targetId) {
        this.articleTargetService.deleteTarget(targetId);
        return ApiResponse.ok();
    }

}

package sample.newsdata.api.controller.article.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import sample.newsdata.domain.article.Article;
import sample.newsdata.domain.article.ArticleSource;

public record CreateArticleRequest(
        @NotEmpty(message = "키워드는 필수입니다.")
        String keyword,
        @NotNull(message = "뉴스 사이트는 필수입니다.")
        ArticleSource articleSource
) {}

package sample.newsdata.domain.article.response;

import sample.newsdata.domain.article.ArticleSource;
import sample.newsdata.domain.article.ArticleTarget;

public record ArticleTargetResponse(
        Long id,
        String keyword,
        ArticleSource articleSource
) {

    public static ArticleTargetResponse of(ArticleTarget articleTarget) {
        return new ArticleTargetResponse(
                articleTarget.getId(),
                articleTarget.getKeyword(),
                articleTarget.getArticleSource()
        );
    }

}

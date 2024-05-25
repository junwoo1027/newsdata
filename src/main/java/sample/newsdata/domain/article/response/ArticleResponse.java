package sample.newsdata.domain.article.response;

import sample.newsdata.domain.article.Article;
import sample.newsdata.domain.article.ArticleSource;

public record ArticleResponse(
        Long id,
        String title,
        String description,
        String link,
        String keyword,
        ArticleSource articleSource
) {

    public static ArticleResponse of(Article article) {
        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getDescription(),
                article.getLink(),
                article.getKeyword(),
                article.getArticleSource()
        );
    }

}

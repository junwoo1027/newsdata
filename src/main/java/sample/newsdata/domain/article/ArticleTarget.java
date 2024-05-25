package sample.newsdata.domain.article;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.newsdata.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ArticleTarget extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyword;

    @Enumerated(EnumType.STRING)
    private ArticleSource articleSource;

    public ArticleTarget(String keyword, ArticleSource articleSource) {
        this.keyword = keyword;
        this.articleSource = articleSource;
    }

    public void update(String keyword, ArticleSource articleSource) {
        this.keyword = keyword;
        this.articleSource = articleSource;
    }

}


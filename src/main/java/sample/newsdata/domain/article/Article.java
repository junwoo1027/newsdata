package sample.newsdata.domain.article;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.newsdata.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String link;

    private String keyword;

    @Enumerated(EnumType.STRING)
    private ArticleSource articleSource;

    public Article(String title, String description, String link, String keyword, ArticleSource articleSource) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.keyword = keyword;
        this.articleSource = articleSource;
    }

}

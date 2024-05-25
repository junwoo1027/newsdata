package sample.newsdata.domain.article;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleTargetTest {

    @DisplayName("키워드와 뉴스 사이트를 받아 타겟을 업데이트한다.")
    @Test
    void updateTarget() {
        // given
        ArticleTarget articleTarget = new ArticleTarget("자바", ArticleSource.ALL);

        // when
        articleTarget.update("백엔드", ArticleSource.DAUM);

        //then
        assertThat(articleTarget.getKeyword()).isEqualTo("백엔드");
        assertThat(articleTarget.getArticleSource()).isEqualTo(ArticleSource.DAUM);
    }

}
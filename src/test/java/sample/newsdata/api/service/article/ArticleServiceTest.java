package sample.newsdata.api.service.article;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import sample.newsdata.IntegrationTestSupport;
import sample.newsdata.api.controller.article.request.CreateArticleRequest;
import sample.newsdata.domain.article.*;
import sample.newsdata.domain.article.response.ArticleResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest extends IntegrationTestSupport {

    @MockBean
    private NaverArticleScraper naverArticleScraper;

    @MockBean
    private DaumArticleScraper daumArticleScraper;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleTargetRepository articleTargetRepository;

    @Autowired
    private ArticleService articleService;

    @DisplayName("키워드와 뉴스 사이트 정보를 입력받아 뉴스 데이터를 저장한다.")
    @Test
    void createNews() {
        // given
        CreateArticleRequest request = new CreateArticleRequest("test", ArticleSource.DAUM);
        given(daumArticleScraper.fetchArticles(request.keyword())).willReturn(
                List.of(
                        new Article("title1", "desc1", "test", "test", ArticleSource.DAUM),
                        new Article("title2", "desc2", "test", "test", ArticleSource.DAUM),
                        new Article("title3", "desc3", "test", "test", ArticleSource.DAUM)
                )
        );

        // when
        List<ArticleResponse> results = articleService.createArticle(request);

        // then
        assertThat(results).hasSize(3);
        assertThat(results).extracting("title", "description", "articleSource")
                .containsExactlyInAnyOrder(
                        tuple("title1", "desc1", ArticleSource.DAUM),
                        tuple("title2", "desc2", ArticleSource.DAUM),
                        tuple("title3", "desc3", ArticleSource.DAUM)
                );
    }

    @DisplayName("등록된 타겟 정보를 가져와 뉴스 데이터를 저장한다.")
    @Test
    void createArticlesByTargets() {
        // given
        ArticleTarget target = articleTargetRepository.save(new ArticleTarget("개발", ArticleSource.DAUM));
        given(daumArticleScraper.fetchArticles(target.getKeyword())).willReturn(
                List.of(
                        new Article("title1", "desc1", "test", "test", ArticleSource.DAUM),
                        new Article("title2", "desc2", "test", "test", ArticleSource.DAUM),
                        new Article("title3", "desc3", "test", "test", ArticleSource.DAUM)
                )
        );

        // when
        List<ArticleResponse> results = articleService.createArticlesByTargets();

        // then
        assertThat(results).hasSize(3);
        assertThat(results).extracting("title", "description", "articleSource")
                .containsExactlyInAnyOrder(
                        tuple("title1", "desc1", ArticleSource.DAUM),
                        tuple("title2", "desc2", ArticleSource.DAUM),
                        tuple("title3", "desc3", ArticleSource.DAUM)
                );
    }

}
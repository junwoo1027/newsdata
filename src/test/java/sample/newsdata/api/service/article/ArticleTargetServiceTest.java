package sample.newsdata.api.service.article;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sample.newsdata.IntegrationTestSupport;
import sample.newsdata.api.controller.article.request.CreateTargetRequest;
import sample.newsdata.api.controller.article.request.UpdateTargetRequest;
import sample.newsdata.domain.article.ArticleSource;
import sample.newsdata.domain.article.ArticleTarget;
import sample.newsdata.domain.article.ArticleTargetRepository;
import sample.newsdata.domain.article.response.ArticleTargetResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

class ArticleTargetServiceTest extends IntegrationTestSupport {

    @Autowired
    private ArticleTargetRepository articleTargetRepository;

    @Autowired
    private ArticleTargetService articleTargetService;

    @AfterEach
    void clear() {
        articleTargetRepository.deleteAllInBatch();
    }

    @DisplayName("뉴스 수집 설정 목록을 조회한다.")
    @Test
    void getTargets() {
        // given
        articleTargetRepository.saveAll(
                List.of(
                        new ArticleTarget("개발자", ArticleSource.ALL),
                        new ArticleTarget("백엔드", ArticleSource.DAUM),
                        new ArticleTarget("자바", ArticleSource.NAVER)
                )
        );

        // when
        List<ArticleTargetResponse> results = articleTargetService.getTargets();

        // then
        assertThat(results).hasSize(3);
        assertThat(results).extracting("keyword", "articleSource")
                .containsExactlyInAnyOrder(
                        tuple("개발자", ArticleSource.ALL),
                        tuple("백엔드", ArticleSource.DAUM),
                        tuple("자바", ArticleSource.NAVER)
                );

    }

    @DisplayName("키워드와 뉴스 사이트를 입력받아 수집 설정을 등록한다.")
    @Test
    void createTarget() {
        // given
        CreateTargetRequest request = new CreateTargetRequest("개발자", ArticleSource.NAVER);

        // when
        ArticleTargetResponse result = articleTargetService.createTarget(request);

        // then
        assertThat(result.keyword()).isEqualTo("개발자");
        assertThat(result.articleSource()).isEqualTo(ArticleSource.NAVER);
    }

    @DisplayName("수십 설정 등록 시 이미 등록된 키워드면 예외가 발생한다.")
    @Test
    void createTargetAlreadyRegisteredKeyword() {
        // given
        articleTargetRepository.save(new ArticleTarget("개발자", ArticleSource.ALL));
        CreateTargetRequest request = new CreateTargetRequest("개발자", ArticleSource.NAVER);

        // when && then
        assertThatThrownBy(() -> articleTargetService.createTarget(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Already registered keyword.");
    }

    @DisplayName("키워드와 뉴스 사이트를 입력받아 수집 설정을 업데이트한다.")
    @Test
    void updateTarget() {
        // given
        ArticleTarget target = articleTargetRepository.save(new ArticleTarget("개발자", ArticleSource.ALL));
        UpdateTargetRequest request = new UpdateTargetRequest("자바", ArticleSource.NAVER);

        // when
        ArticleTargetResponse result = articleTargetService.updateTarget(target.getId(), request);

        // then
        assertThat(result.keyword()).isEqualTo("자바");
        assertThat(result.articleSource()).isEqualTo(ArticleSource.NAVER);
    }

    @DisplayName("뉴스 수집 설정 업데이트 시 해당 데이터가 없으면 예외가 발생한다..")
    @Test
    void updateTargetNotTarget() {
        // given
        UpdateTargetRequest request = new UpdateTargetRequest("자바", ArticleSource.NAVER);

        // when && then
        assertThatThrownBy(() -> articleTargetService.updateTarget(1L, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Not found target.");
    }

    @DisplayName("타겟 id를 받아 해당 데이터를 삭제한다.")
    @Test
    void deleteTarget() {
        // given
        ArticleTarget target = articleTargetRepository.save(new ArticleTarget("개발자", ArticleSource.ALL));

        // when
        articleTargetService.deleteTarget(target.getId());

        // then
        List<ArticleTarget> targets = articleTargetRepository.findAll();

        assertThat(targets).hasSize(0);
    }

    @DisplayName("뉴스 수집 설정 삭제 시 해당 데이터가 없으면 예외가 발생한다..")
    @Test
    void deleteTargetNotTarget() {
        // given

        // when && then
        assertThatThrownBy(() -> articleTargetService.deleteTarget(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Not found target.");
    }

}
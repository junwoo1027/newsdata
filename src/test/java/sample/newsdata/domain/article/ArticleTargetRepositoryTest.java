package sample.newsdata.domain.article;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sample.newsdata.IntegrationTestSupport;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class ArticleTargetRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ArticleTargetRepository articleTargetRepository;

    @DisplayName("키워드로 등록된 타겟이있는지 조회한다.")
    @Test
    void existsByKeyword() {
        // given
        articleTargetRepository.save(new ArticleTarget("자바", ArticleSource.ALL));

        // when
        boolean result = articleTargetRepository.existsByKeyword("자바");

        // then
        assertThat(result).isTrue();
    }

}
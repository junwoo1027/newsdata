package sample.newsdata.domain.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTargetRepository extends JpaRepository<ArticleTarget, Long> {

    boolean existsByKeyword(String keyword);

}

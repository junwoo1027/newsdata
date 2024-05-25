package sample.newsdata.api.service.article;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.newsdata.api.controller.article.request.CreateTargetRequest;
import sample.newsdata.domain.article.ArticleTarget;
import sample.newsdata.domain.article.ArticleTargetRepository;
import sample.newsdata.domain.article.response.ArticleTargetResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleTargetService {

    private final ArticleTargetRepository articleTargetRepository;

    public List<ArticleTargetResponse> getTargets() {
        List<ArticleTarget> targets = articleTargetRepository.findAll();
        return targets.stream()
                .map(ArticleTargetResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public ArticleTargetResponse createTarget(CreateTargetRequest request) {
        boolean existsTargetByKeyword = articleTargetRepository.existsByKeyword(request.keyword());
        if (existsTargetByKeyword) {
            throw new IllegalArgumentException("Already registered keyword.");
        }

        ArticleTarget target = articleTargetRepository.save(new ArticleTarget(request.keyword(), request.articleSource()));
        return ArticleTargetResponse.of(target);
    }

    @Transactional
    public void deleteTarget(Long targetId) {
        ArticleTarget articleTarget = getArticleTarget(targetId);
        articleTargetRepository.delete(articleTarget);
    }

    private ArticleTarget getArticleTarget(Long targetId) {
        ArticleTarget articleTarget = articleTargetRepository.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("Not found target."));
        return articleTarget;
    }

}

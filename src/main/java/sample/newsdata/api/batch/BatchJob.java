package sample.newsdata.api.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sample.newsdata.api.service.article.ArticleService;

@Component
@RequiredArgsConstructor
public class BatchJob {

    private final ArticleService articleService;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정 실행
    public void schedulingJob() {
        this.articleService.createArticlesByTargets();
    }

}

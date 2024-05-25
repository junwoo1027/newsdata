package sample.newsdata.api.controller.article;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import sample.newsdata.ControllerTestSupport;
import sample.newsdata.api.controller.article.request.CreateArticleRequest;
import sample.newsdata.api.controller.article.request.CreateTargetRequest;
import sample.newsdata.domain.article.ArticleSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleControllerTest extends ControllerTestSupport {

    @DisplayName("뉴스 데이터를 수집한다.")
    @Test
    void createArticleTarget() throws Exception {
        CreateArticleRequest request = new CreateArticleRequest("개발자", ArticleSource.DAUM);

        mockMvc.perform(post("/api/v1/articles")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("뉴스 데이터를 수집할 때 키워드는 필수값이다.")
    @Test
    void createArticleWithoutKeyword() throws Exception {
        CreateTargetRequest request = new CreateTargetRequest(null, ArticleSource.DAUM);

        mockMvc.perform(post("/api/v1/articles")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("키워드는 필수입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("뉴스 데이터를 수집할 때 뉴스 사이트 정보는 필수값이다.")
    @Test
    void createArticleWithoutArticleSource() throws Exception {
        CreateTargetRequest request = new CreateTargetRequest("개발자", null);

        mockMvc.perform(post("/api/v1/articles")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("뉴스 사이트는 필수입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}
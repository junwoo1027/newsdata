package sample.newsdata.api.controller.article;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import sample.newsdata.ControllerTestSupport;
import sample.newsdata.api.controller.article.request.CreateTargetRequest;
import sample.newsdata.domain.article.ArticleSource;
import sample.newsdata.domain.article.response.ArticleTargetResponse;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleTargetControllerTest extends ControllerTestSupport {

    @DisplayName("뉴스 수집 타겟 목록을 조회한다.")
    @Test
    void getArticleTargets() throws Exception {
        List<ArticleTargetResponse> result = List.of();

        given(articleTargetService.getTargets()).willReturn(result);

        mockMvc.perform(get("/api/v1/targets"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray());
    }

    @DisplayName("뉴스 수집 타겟을 등록한다.")
    @Test
    void createArticleTarget() throws Exception {
        CreateTargetRequest request = new CreateTargetRequest("개발자", ArticleSource.DAUM);

        mockMvc.perform(post("/api/v1/targets")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("뉴스 수집 타겟을 등록할 때 키워드는 필수값이다.")
    @Test
    void createArticleTargetWithoutKeyword() throws Exception {
        CreateTargetRequest request = new CreateTargetRequest(null, ArticleSource.DAUM);

        mockMvc.perform(post("/api/v1/targets")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("키워드는 필수입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("뉴스 수집 타겟을 등록할 때 뉴스 사이트 정보는 필수값이다.")
    @Test
    void createArticleTargetWithoutArticleSource() throws Exception {
        CreateTargetRequest request = new CreateTargetRequest("개발자", null);

        mockMvc.perform(post("/api/v1/targets")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("뉴스 사이트는 필수입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("뉴스 수집 타겟을 삭제한다.")
    @Test
    void deleteArticleTarget() throws Exception {
        mockMvc.perform(delete("/api/v1/targets/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
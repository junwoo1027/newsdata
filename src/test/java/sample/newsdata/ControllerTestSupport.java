package sample.newsdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import sample.newsdata.api.controller.user.UserController;
import sample.newsdata.api.service.user.OauthService;
import sample.newsdata.api.service.user.UserService;
import sample.newsdata.api.support.UserArgumentResolver;

@WebMvcTest(controllers = {
        UserController.class,
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserArgumentResolver userArgumentResolver;

    @MockBean
    protected UserService userService;

    @MockBean
    protected OauthService oauthService;

}

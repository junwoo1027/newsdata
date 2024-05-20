package sample.newsdata.api.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "githubUser", url = "https://api.github.com")
public interface GitHubUserClient {

    @GetMapping("/user")
    Map<String, Object> getUser(@RequestHeader("Authorization") String token);

}

package sample.newsdata.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OauthConfig {

    @Value("${oauth.github.client-id}")
    public String clientId;

    @Value("${oauth.github.client-secret}")
    public String clientSecret;
}

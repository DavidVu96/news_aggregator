package uk.co.davidvu.newsaggregator.technews.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:application.yml")
public class NewsApiConfiguration {

    @Value("${newsapi.token}")
    private String apiToken;

    @Value("${newsapi.url}")
    private String baseUrl;

    @Bean("newsApiClient")
    public RestTemplate newsAPIClient(RestTemplateBuilder builder) {
        return builder
                .rootUri(baseUrl)
                .defaultHeader("X-Api-Key", apiToken)
                .build();
    }
}

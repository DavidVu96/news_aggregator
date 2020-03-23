package uk.co.davidvu.newsaggregator.technews.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsApiArticleResponse {
    private String status;
    private Integer totalResults;
    private List<Article> articles;
}

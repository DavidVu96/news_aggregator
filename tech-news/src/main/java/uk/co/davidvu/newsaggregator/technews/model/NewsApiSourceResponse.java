package uk.co.davidvu.newsaggregator.technews.model;

import lombok.Data;

import java.util.List;

@Data
public class NewsApiSourceResponse {
    private String status;

    private List<Source> sources;
}

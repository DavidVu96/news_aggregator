package uk.co.davidvu.newsaggregator.technews.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uk.co.davidvu.newsaggregator.technews.dao.ArticleRepository;
import uk.co.davidvu.newsaggregator.technews.dao.SourceRepository;
import uk.co.davidvu.newsaggregator.technews.model.Article;
import uk.co.davidvu.newsaggregator.technews.model.Source;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    SourceRepository sourceRepository;

    public List<Article> getRecentNews(){
        log.debug("Querying all news in past hour from " + Instant.now().toString());
        return articleRepository.findAllByPublishedAtBefore(Instant.now().minus(1, ChronoUnit.HOURS));
    }

    public List<Article> getBySourceName(String sourceName){
        Source source = sourceRepository.findByName(sourceName);
        return articleRepository.findAllBySource(source);
    }

    public Page<Article> getRecentNewsPaginated(int page, int size){
        log.debug("Querying all news in past hour from " + Instant.now().toString());
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"publishedAt"));
        return articleRepository.findAllByPublishedAtBefore(Instant.now().minus(1, ChronoUnit.HOURS), pageable);
    }
}

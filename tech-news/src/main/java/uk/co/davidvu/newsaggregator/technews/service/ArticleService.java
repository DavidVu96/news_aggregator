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

    /**
     * Retrieve all news in past hour from the db
     * @return List of articles
     */
    public List<Article> getRecentNews(){
        log.debug("Querying all news in past hour from " + Instant.now().toString());
        return articleRepository.findAllByPublishedAtBefore(Instant.now().minus(1, ChronoUnit.HOURS));
    }

    /**
     * Query for all article from a source
     * Do not use this normally, since it can create huge db strain
     * @param sourceName name of the source e.g. BBC News, ABC News...
     * @return list of all articles from that source
     */
    public List<Article> getBySourceName(String sourceName){
        Source source = sourceRepository.findByName(sourceName);
        return articleRepository.findAllBySource(source);
    }

    public Page<Article> getBySourceNamePaginated(String sourceName, int page, int size){
        Source source = sourceRepository.findByName(sourceName);
        return articleRepository.findAllBySource(source, PageRequest.of(page,size));
    }

    /**
     * Get recent news but return in page
     * @param page the offset/current page, start at 0
     * @param size number of article per page
     * @return page object with content are articles
     */
    public Page<Article> getRecentNewsPaginated(int page, int size){
        log.debug("Querying all news in past hour from " + Instant.now().toString());
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"publishedAt"));
        return articleRepository.findAllByPublishedAtBefore(Instant.now().minus(1, ChronoUnit.HOURS), pageable);
    }
}

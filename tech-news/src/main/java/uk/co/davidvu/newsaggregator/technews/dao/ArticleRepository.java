package uk.co.davidvu.newsaggregator.technews.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.co.davidvu.newsaggregator.technews.model.Article;
import uk.co.davidvu.newsaggregator.technews.model.Source;

import java.time.Instant;
import java.util.List;

@Repository
public interface ArticleRepository extends CrudRepository<Article, String> {
//    List<Article> findAllBeforePublishedat(Instant publishedAt);
    List<Article> findAllBySource(Source source);
}

package uk.co.davidvu.newsaggregator.technews.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.co.davidvu.newsaggregator.technews.model.Article;

@Repository
public interface NewsArticleRepository extends CrudRepository<Article, String> {
}

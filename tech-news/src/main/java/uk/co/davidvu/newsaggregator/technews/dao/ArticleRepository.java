package uk.co.davidvu.newsaggregator.technews.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.co.davidvu.newsaggregator.technews.model.Article;
import uk.co.davidvu.newsaggregator.technews.model.Source;

import java.time.Instant;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {
    List<Article> findAllBySource(Source source);
    Page<Article> findAllBySource(Source source, Pageable pageable);
    List<Article> findAllByPublishedAtBefore(Instant publishedAt);
    Page<Article> findAllByPublishedAtBefore(Instant publishedAt, Pageable pageable);
}

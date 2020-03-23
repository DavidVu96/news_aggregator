package uk.co.davidvu.newsaggregator.technews.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.co.davidvu.newsaggregator.technews.model.Source;

import java.util.List;

@Repository
public interface SourceRepository extends JpaRepository<Source, String> {
    Source findByName(String name);
    List<Source> findAllByName(String name);
}

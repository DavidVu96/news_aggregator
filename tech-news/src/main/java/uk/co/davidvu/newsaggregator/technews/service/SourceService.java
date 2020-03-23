package uk.co.davidvu.newsaggregator.technews.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.davidvu.newsaggregator.technews.dao.SourceRepository;
import uk.co.davidvu.newsaggregator.technews.model.Source;

@Service
@Slf4j
public class SourceService {

    @Autowired
    SourceRepository sourceRepository;

    public Iterable<Source> getAllSources() {
        return sourceRepository.findAll();
    }
}

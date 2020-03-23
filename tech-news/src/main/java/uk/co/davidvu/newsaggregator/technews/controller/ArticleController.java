package uk.co.davidvu.newsaggregator.technews.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.co.davidvu.newsaggregator.technews.dao.ArticleRepository;
import uk.co.davidvu.newsaggregator.technews.dao.SourceRepository;
import uk.co.davidvu.newsaggregator.technews.model.Article;
import uk.co.davidvu.newsaggregator.technews.model.Source;

import javax.websocket.server.PathParam;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    SourceRepository sourceRepository;

    @GetMapping(path = "/recent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Article>> getRecentNews(){
        log.debug("Querying all news in past hour from " + Instant.now().toString());
        return new ResponseEntity<>(articleRepository.findAllByPublishedAtBefore(Instant.now().minus(1, ChronoUnit.HOURS)), HttpStatus.OK);
    }

    @GetMapping(path = "/source", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Article>> getBySourceName(@PathParam("source") String sourceName){
        Source source = sourceRepository.findByName(sourceName);
        return new ResponseEntity<>(articleRepository.findAllBySource(source), HttpStatus.OK);
    }

    @GetMapping(path = "/paged_recent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Article>> getRecentNewsPaginated(@RequestParam("page") int page, @RequestParam("size") int size){
        log.debug("Querying all news in past hour from " + Instant.now().toString());
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"publishedAt"));
        return new ResponseEntity<>(articleRepository.findAllByPublishedAtBefore(Instant.now().minus(1, ChronoUnit.HOURS), pageable), HttpStatus.OK);
    }
}

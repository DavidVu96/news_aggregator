package uk.co.davidvu.newsaggregator.technews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    SourceRepository sourceRepository;

//    @GetMapping(path = "/recent", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<Article >> getRecentNews(){
//        return new ResponseEntity<>(articleRepository.findAllBeforePublishedat(Instant.now().minus(1, ChronoUnit.HOURS)), HttpStatus.OK);
//    }

    @GetMapping(path = "/source", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Article >> getBySourceName(@PathParam("source") String sourceName){
        Source source = sourceRepository.findByName(sourceName);
        return new ResponseEntity<>(articleRepository.findAllBySource(source), HttpStatus.OK);
    }
}

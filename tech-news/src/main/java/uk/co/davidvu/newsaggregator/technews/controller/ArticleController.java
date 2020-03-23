package uk.co.davidvu.newsaggregator.technews.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.co.davidvu.newsaggregator.technews.model.Article;
import uk.co.davidvu.newsaggregator.technews.service.ArticleService;

import javax.websocket.server.PathParam;
import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticleController {

    @Autowired
    ArticleService service;

    @GetMapping(path = "/recent_all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Article>> getRecentNews() {
        log.debug("Querying all news in past hour from " + Instant.now().toString());
        return new ResponseEntity<>(service.getRecentNews(), HttpStatus.OK);
    }

    @GetMapping(path = "/source_all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Article>> getBySourceName(@PathParam("source") String sourceName) {
        log.debug("Finding article with source " + sourceName);
        return new ResponseEntity<>(service.getBySourceName(sourceName), HttpStatus.OK);
    }

    @GetMapping(path = "/source", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Article>> getBySourceNamePaged(@PathParam("source") String sourceName, @RequestParam("page") int page, @RequestParam("size") int size) {
        log.debug("Finding article with source " + sourceName);
        return new ResponseEntity<>(service.getBySourceNamePaginated(sourceName, page, size), HttpStatus.OK);
    }

    @GetMapping(path = "/recent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Article>> getRecentNewsPaginated(@RequestParam("page") int page, @RequestParam("size") int size) {
        log.debug("Query page " + page + " with size " + size);
        return new ResponseEntity<>(service.getRecentNewsPaginated(page, size), HttpStatus.OK);
    }
}

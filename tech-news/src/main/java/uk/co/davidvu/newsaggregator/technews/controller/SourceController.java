package uk.co.davidvu.newsaggregator.technews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.co.davidvu.newsaggregator.technews.dao.SourceRepository;
import uk.co.davidvu.newsaggregator.technews.model.Source;

@Controller
@RequestMapping(path = "/source")
public class SourceController {

    @Autowired
    SourceRepository sourceRepository;

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Source>> getBySourceName() {
        return new ResponseEntity<>(sourceRepository.findAll(), HttpStatus.OK);
    }
}

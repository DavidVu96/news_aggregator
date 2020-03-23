package uk.co.davidvu.newsaggregator.technews.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.net.URL;
import java.time.Instant;

@Entity
@Data
@Table
public class Article {

    @Id
    @Column
    private String title;

    @Column
    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private URL url;

    @Column(columnDefinition = "TEXT")
    private URL urlToImage;

    @Column
    private Instant publishedAt;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JsonBackReference
    private Source source;
}
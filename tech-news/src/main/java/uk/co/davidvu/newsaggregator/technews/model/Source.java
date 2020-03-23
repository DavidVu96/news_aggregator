package uk.co.davidvu.newsaggregator.technews.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.net.URL;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table
public class Source{

    @Id
    private String id;
    @Column
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private URL url;
    @Column
    private String category;
    @Column
    private String language;
    @Column
    private String country;


    @OneToMany
    @JsonManagedReference
    private List<Article> articles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Source)) return false;
        Source source = (Source) o;
        return getName().equals(source.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}

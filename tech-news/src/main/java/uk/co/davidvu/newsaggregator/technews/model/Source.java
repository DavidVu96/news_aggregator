package uk.co.davidvu.newsaggregator.technews.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table
public class Source{

    @Id
    private String name;
    private String id;


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

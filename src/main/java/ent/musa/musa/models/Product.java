package ent.musa.musa.models;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Product {
    @Id @GeneratedValue Long id;
    String title;
    String description;

    @JsonCreator
    Product(@JsonProperty("title") String title,
            @JsonProperty("description") String description
    ) {
        this.title = title;
        this.description = description;
    }

    public Product() {}

    public Long getId() { return this.id; }
    public String getTitle() { return this.title; }
    public String getDescription() { return this.description; }

    public void setTitle(String title) { this.title = title; }

    public boolean equalsAs(Object object) {
        if (this == object) return true;
        if (!(object instanceof Product product)) return false;

        return Objects.equals(this.id, product.id)
                && Objects.equals(this.title, product.title)
                && Objects.equals(this.description, product.description);
    }

    public int hashProductDetail() {
        return Objects.hash(this.id, this.title, this.description);
    }
}

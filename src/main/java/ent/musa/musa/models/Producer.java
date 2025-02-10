package ent.musa.musa.models;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Producer {
    @Id @GeneratedValue Long id;
    String name;
    String email;

    @JsonCreator
    public Producer (@JsonProperty("name") String name,
                     @JsonProperty("email") String email
    ) {
        this.name = name;
        this.email = email;
    }

    public Producer() {}

    public Long getId() { return this.id; }
    public String getName() { return this.name; }
    public String getEmail() { return this.email; }

    public void setUsername(String name) { this.name = name; }

    public boolean equalsAs(Object object) {
        if (this == object) return true;
        if (!(object instanceof Producer producer)) return false;

        return Objects.equals(this.id, producer.id)
                && Objects.equals(this.name, producer.name)
                && Objects.equals(this.email, producer.email);
    }

    public int hashProducerDetail() {
        return Objects.hash(this.id, this.name, this.email);
    }
}

package ent.musa.musa.models;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Customer extends RepresentationModel<Customer> {
    @Id @GeneratedValue Long id;
    String username;
    String email;

    @JsonCreator
    public Customer(@JsonProperty("username") String username) {
        this.username = username;
    }

    public Customer() {}

    public Long getId() { return this.id; }
    public String getUsername() { return this.username; }
    public String email() { return this.email; }

    public void setUsername(String username) { this.username = username; }

    public boolean equalsAs(Object object) {
        if (this == object) return true;
        if (!(object instanceof Customer customer)) return false;

        return Objects.equals(this.id, customer.id)
            && Objects.equals(this.username, customer.username)
            && Objects.equals(this.email, customer.email);
    }

    public int hashCustomerDetail() {
        return Objects.hash(this.id, this.username, this.email);
    }
}

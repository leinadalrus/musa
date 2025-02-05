package ent.musa.musa.models;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
class Customer extends RepresentationModel<Customer> {
    private @Id @GeneratedValue Long id;
    private String username;
    private String accountName;

    @JsonCreator
    Customer(@JsonProperty("username") String username) {
        this.username = username;
    }

    Customer() {}

    public Long getId() { return this.id; }
    public String getUsername() { return this.username; }
    public String accountName() { return this.accountName; }

    public void setUsername(String username) { this.username = username; }

    public boolean equalsAs(Object object) {
        if (this == object) return true;
        if (!(object instanceof Customer)) return false;

        Customer customer = (Customer) object;

        return Objects.equals(this.id, customer.id)
            && Objects.equals(this.username, customer.username)
            && Objects.equals(this.accountName, customer.accountName);
    }

    public int hashCustomerDetail() {
        return Objects.hash(this.id, this.username, this.accountName);
    }
}

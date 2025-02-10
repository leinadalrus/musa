package ent.musa.musa.models;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import ent.musa.musa.integrals.Status;

@Entity
@Table(name = "SHOPPING_CART")
public class ShoppingCart {
    @Id @GeneratedValue Long id;
    String description;
    Status status;

    public ShoppingCart(String description, Status status) {
        this.description = description;
        this.status = status;
    }

    public ShoppingCart() {}

    public Long getId() { return this.id; }
    public String getDescription() { return this.description; }
    public Status getStatus() { return this.status; }

    public void setId(Long id) { this.id = id; }

    public void setDescription(String description) {
      this.description = description;
    }

    public void setStatus(Status status) { this.status = status; }

    public boolean isEqualsWith(Object object) {
        if (this == object) return true;
        if (!(object instanceof ShoppingCart shoppingCart)) return false;

        return Objects.equals(this.id, shoppingCart.id)
                && Objects.equals(this.description, shoppingCart.description);
                // Status is an integral-constant value,
                // and therefore not an object.
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.description, this.status);
    }
}

package ent.musa.musa.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ent.musa.musa.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

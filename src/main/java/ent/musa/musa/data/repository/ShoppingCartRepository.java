package ent.musa.musa.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ent.musa.musa.models.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {}

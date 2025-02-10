package ent.musa.musa.data;

public class ShoppingCartNotFoundException extends RuntimeException {
    public ShoppingCartNotFoundException(Long id) { super(String.valueOf(id)); }
}

package ent.musa.musa.data;

class ShoppingCartNotFoundException extends RuntimeException {
    ShoppingCartNotFoundException(Long id) { super(id); }
}

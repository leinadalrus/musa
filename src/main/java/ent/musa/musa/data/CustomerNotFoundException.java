package ent.musa.musa.data;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super(String.valueOf(id));
    }
}

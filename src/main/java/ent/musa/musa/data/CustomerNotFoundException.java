package ent.musa.musa.data;

class CustomerNotFoundException extends RuntimeException {
    CustomerNotFoundException(Long id) {
        super(id);
    }
}

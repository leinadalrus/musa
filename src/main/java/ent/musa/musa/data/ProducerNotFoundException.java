package ent.musa.musa.data;

public class ProducerNotFoundException extends RuntimeException {
    public ProducerNotFoundException(Long message) {
        super(String.valueOf(message));
    }
}

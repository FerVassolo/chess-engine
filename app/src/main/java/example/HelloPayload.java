package example;

public record HelloPayload(String name) {
    @Override
    public String toString() {
        return "Hello " + name();
    }
}

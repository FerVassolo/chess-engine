package client.listeners;

public record HelloPayload(String name) {
    @Override
    public String toString() {
        return "Hello " + name();
    }
}
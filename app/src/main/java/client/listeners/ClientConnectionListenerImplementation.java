package edu.austral.dissis.client;

import client.GameClient;
import edu.austral.ingsis.clientserver.ClientConnectionListener;

public class ClientConnectionListenerImplementation implements ClientConnectionListener {
    private final GameClient client;

    public ClientConnectionListenerImplementation(GameClient client) {
        this.client = client;
    }

    @Override
    public void handleConnection() {
        client.connect();
    }

    @Override
    public void handleConnectionClosed() {
        client.disconnect();
    }
}

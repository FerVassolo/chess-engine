package client.listeners;

import edu.austral.ingsis.clientserver.ClientConnectionListener;

public class MyClientConnectionListener implements ClientConnectionListener {

    @Override
    public void handleConnection() {
        System.out.println("Client has connected to server");
    }

    // When server disconects, the client gets this message
    @Override
    public void handleConnectionClosed() {
        System.out.println("Client was disconnected from server");
    }
}
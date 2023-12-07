package example;

import edu.austral.ingsis.clientserver.ServerConnectionListener;
import org.jetbrains.annotations.NotNull;

public class MyServerConnectionListener implements ServerConnectionListener {

    // When a client conects it sends this message to the server
    // e.g: Client connected with id 784f43fffe4e78ff-000010b2-00000002-d4df92df9a61d317-3d53142a
    @Override
    public void handleClientConnection(@NotNull String clientId) {
        System.out.println("Client connected with id " + clientId);
    }

    // When a client disconects it sends this message to the server
    // e.g: Client with id 784f43fffe4e78ff-000010b2-00000001-34c6a9122260f3a0-c269cb6b disconnected
    @Override
    public void handleClientConnectionClosed(@NotNull String clientId) {
        System.out.println("Client with id " + clientId + " disconnected");

    }
}

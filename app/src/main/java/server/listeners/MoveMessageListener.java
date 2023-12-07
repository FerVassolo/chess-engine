package server.listeners;

import edu.austral.dissis.chess.gui.Move;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import org.jetbrains.annotations.NotNull;
import server.GameServer;

public class MoveMessageListener implements MessageListener<Move> {

    GameServer server;
    public MoveMessageListener(GameServer server){
        this.server = server;
    }
    @Override
    public void handleMessage(@NotNull Message<Move> message) {
        server.applyMove(message.getPayload());
    }
}

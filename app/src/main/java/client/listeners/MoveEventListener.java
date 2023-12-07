package client.listeners;

import client.GameClient;
import edu.austral.dissis.chess.gui.GameEventListener;
import edu.austral.dissis.chess.gui.Move;
import org.jetbrains.annotations.NotNull;

public class MoveEventListener implements GameEventListener {

    GameClient client;
    public MoveEventListener(GameClient client){
        this.client = client;
    }

    @Override
    public void handleMove(@NotNull Move move) {
        client.sendMove(move);
        System.out.println("Move sent!");
    }
}

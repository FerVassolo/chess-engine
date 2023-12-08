package server.listeners;

import commons.board.Board;
import commons.game.Color;
import commons.game.Game;
import edu.austral.dissis.chess.gui.*;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.ServerConnectionListener;
import org.jetbrains.annotations.NotNull;
import server.GameServer;

import java.util.List;


//.withConnectionListener(InitListener(this))
public class InitConnectionListener implements ServerConnectionListener {
    GameServer gameServer;

    public InitConnectionListener(GameServer gameServer){
        this.gameServer = gameServer;
    }

    public void gameData(){
        Game game = gameServer.getGame();
        Board board = game.getBoard();
        List<ChessPiece> pieces = new BoardAdapter().engineBoardToUIBoard(board);
        BoardSize boardSize = new BoardSize(board.getWidth(), board.getHeight());
        Color color = game.getCurrentPlayer().getColor();
        PlayerColor UIColor = new BoardAdapter().convertColor(color);
        gameServer.getServer().broadcast(new Message<>("init-game",new InitialState(boardSize,pieces,UIColor)));

    }

    @Override
    public void handleClientConnection(@NotNull String clientId) {
        gameData();
        System.out.println("Client " + clientId + " has connected");
    }

    @Override
    public void handleClientConnectionClosed(@NotNull String clientId) {
        System.out.println("Client " + clientId + " has disconnected");
    }
}

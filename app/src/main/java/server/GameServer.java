package server;

import com.fasterxml.jackson.core.type.TypeReference;
import commons.board.Position;
import commons.game.Color;
import commons.game.Game;
import commons.result.EndgameResult;
import commons.result.InvalidMoveResult;
import commons.result.MoveResult;
import commons.result.ValidMoveResult;
import edu.austral.dissis.chess.gui.*;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.Server;
import edu.austral.ingsis.clientserver.ServerBuilder;
import edu.austral.ingsis.clientserver.netty.server.NettyServerBuilder;
import server.listeners.InitConnectionListener;
import server.listeners.MoveMessageListener;

import java.util.List;

public class GameServer {
    private Game game;
    private Server server;
    private ServerBuilder serverBuilder = NettyServerBuilder.Companion.createDefault();

    public GameServer(Game game) {
        this.game = game;
        this.server = buildServer();
        this.server.start();
    }

    public Game getGame(){
        return game;
    }

    public Server getServer(){
        return server;
    }

    /**Three options <br>
     * 1. Moves and returns moved board
     * 2. Invalid Move and returns error message
     * 3. Game has ended and returns endgame.
     * */
    public void applyMove(Move move){
        BoardAdapter boardAdapter = new BoardAdapter();
        Position from = boardAdapter.kotlinPositionIntoJavaPosition(move.getFrom());
        Position to = boardAdapter.kotlinPositionIntoJavaPosition(move.getTo());
        MoveResult result = game.move(game.getLastBoard(), from, to);
        System.out.println(result);

        if (result instanceof ValidMoveResult) {
            server.broadcast(new Message("new-game-state", handleNewGameState((ValidMoveResult) result)));
        } else if (result instanceof InvalidMoveResult) {
            server.broadcast(new Message("invalid-move", new InvalidMove(((InvalidMoveResult) result).getErrorMessage())));
        } else if (result instanceof EndgameResult) {
            server.broadcast(new Message("endgame", new GameOver(winnerColor((EndgameResult) result))));
        }
    }

    private edu.austral.dissis.chess.gui.MoveResult handleNewGameState(ValidMoveResult result) {
        Game game = result.getGame();
        List<ChessPiece> pieces = new BoardAdapter().engineBoardToUIBoard(game.getLastBoard());
        PlayerColor color;
        if (game.currentTurn().getColor() == Color.WHITE) {
            color = PlayerColor.BLACK;
        } else {
            color = PlayerColor.WHITE;
        }
        return new NewGameState(pieces, color);
    }

    public Server buildServer(){
        final Server client = serverBuilder
                .withPort(8095)
                .withConnectionListener(new InitConnectionListener(this)) // when a client connects
                .addMessageListener("movement", new TypeReference<>() {}, new MoveMessageListener(this))
                .build();

        return client;
    }

    public PlayerColor winnerColor(EndgameResult result){
        BoardAdapter adapter = new BoardAdapter();
        Color color = result.getWinner().getColor();
        return adapter.convertColor(color);
    }


}

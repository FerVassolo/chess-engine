package commons.result;

import commons.board.Board;
import commons.game.Game;

public class ValidMoveResult implements MoveResult{
    Game game;

    public ValidMoveResult(Game game){
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}

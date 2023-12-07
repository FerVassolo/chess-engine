package commons.rules.endgameRules;

import commons.game.Game;
import commons.board.Board;

public interface EndGameRule {

    // If any endgae rule is true, then the game ends
    public boolean checkEndRule(Game game, Board board);

}

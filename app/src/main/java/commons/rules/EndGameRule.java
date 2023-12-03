package commons.rules;

import commons.game.Game;
import commons.game.Board;

public interface EndGameRule {

    // If any endgae rule is true, then the game ends
    public boolean checkEndRule(Game game, Board board);

}

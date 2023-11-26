package rules;

import game.Game;
import game.Board;

public interface EndGameRule {

    // If any endgae rule is true, then the game ends
    public boolean checkEndRule(Game game, Board board);

}

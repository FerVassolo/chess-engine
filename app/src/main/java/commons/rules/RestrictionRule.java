package commons.rules;

import commons.game.Position;
import commons.game.Board;

public interface RestrictionRule {

    /*Validates if the new position is valid*/
    /*If false, it is invalid, if true it is valid*/
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board);

    public String errorMessage();
}

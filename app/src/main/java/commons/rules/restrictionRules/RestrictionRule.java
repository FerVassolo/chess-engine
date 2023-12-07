package commons.rules.restrictionRules;

import commons.board.Position;
import commons.board.Board;

public interface RestrictionRule {

    /*Validates if the new position is valid*/
    /*If false, it is invalid, if true it is valid*/
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board);

    public String errorMessage();
}

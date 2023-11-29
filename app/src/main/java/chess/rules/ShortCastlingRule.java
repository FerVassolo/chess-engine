package chess.rules;

import commons.Board;
import commons.Position;

import java.util.ArrayList;

public class ShortCastlingRule implements SpecialRule{

    @Override
    public boolean specialRuleIsActive(Position currentPosition, ArrayList<Board> historyOfBoards) {
        return false;
    }

    @Override
    public MovementRule[] getMovementRules() {
        return new MovementRule[0];
    }

    @Override
    public RestrictionRule[] getRestrictionRules() {
        return new RestrictionRule[0];
    }
}
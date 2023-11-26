package rules;

import game.Board;
import game.Position;

import java.util.ArrayList;

public class LongCastlingRule implements SpecialRule{

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

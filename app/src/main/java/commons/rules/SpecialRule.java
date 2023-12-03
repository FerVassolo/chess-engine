package commons.rules;

import commons.game.Position;
import commons.game.Board;

import java.util.ArrayList;

public interface SpecialRule {

    public boolean specialRuleIsActive(Position currentPosition, ArrayList<Board> historyOfBoards);

    public MovementRule[] getMovementRules();

    public RestrictionRule[] getRestrictionRules();

}
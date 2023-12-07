package commons.rules.commonSpecialRules;

import commons.board.Position;
import commons.board.Board;
import commons.rules.restrictionRules.RestrictionRule;
import commons.rules.movementRules.MovementRule;

import java.util.ArrayList;

public interface SpecialRule {

    public boolean specialRuleIsActive(Position currentPosition, ArrayList<Board> historyOfBoards);

    public MovementRule[] getMovementRules();

    public RestrictionRule[] getRestrictionRules();

}
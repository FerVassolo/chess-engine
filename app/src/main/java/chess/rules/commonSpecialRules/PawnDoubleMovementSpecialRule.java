package chess.rules.commonSpecialRules;

import commons.board.Position;
import commons.board.Board;
import commons.rules.commonSpecialRules.SpecialRule;
import commons.rules.restrictionRules.RestrictionRule;
import commons.rules.restrictionRules.VerticalMaxQuantityRule;
import commons.rules.movementRules.MovementRule;

import java.util.ArrayList;
import java.util.Arrays;

public class PawnDoubleMovementSpecialRule implements SpecialRule {

    private final MovementRule[] movementRules;
    private RestrictionRule[] restrictionRules;

    public PawnDoubleMovementSpecialRule(MovementRule[] movementRules, RestrictionRule[] restrictionRules) {
        this.movementRules = movementRules;
        this.restrictionRules = Arrays.copyOf(restrictionRules, restrictionRules.length);
        this.restrictionRules = putNewRestriction(this.restrictionRules, new VerticalMaxQuantityRule(1), new VerticalMaxQuantityRule(2));
    }

    // current position, piece the pawn is at the start of the movement
    @Override
    public boolean specialRuleIsActive(Position currentPosition, ArrayList<Board> historyOfBoards) {
        if(hasBeenAtTheSamePositionAllTheGame(currentPosition, historyOfBoards))
            return true;
        return false;
    }

    public boolean hasBeenAtTheSamePositionAllTheGame(Position currentPosition, ArrayList<Board> historyOfBoards){
        Board firstBoard = historyOfBoards.get(0);
        for(Board board : historyOfBoards){
            if(firstBoard.getPiece(currentPosition) != null) {
                if (board.getPiece(currentPosition).equals(firstBoard.getPiece(currentPosition))){
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public MovementRule[] getMovementRules() {
        return movementRules;
    }

    @Override
    public RestrictionRule[] getRestrictionRules() {
        return restrictionRules;
    }

    private RestrictionRule[] putNewRestriction(RestrictionRule[] restrictionRules, RestrictionRule ruleToRemove, RestrictionRule newRule){
        // if there is already an instance of this object, replace that one with the new one.
        for(int i = 0; i < restrictionRules.length; i++){
            if(restrictionRules[i].getClass() == ruleToRemove.getClass()){
                RestrictionRule[] newRules = restrictionRules;
                newRules[i] = newRule;
                return newRules;
            }
        }
        RestrictionRule[] newRules = Arrays.copyOf(restrictionRules, restrictionRules.length + 1);
        newRules[newRules.length - 1] = newRule;
        return newRules;
    }
}

package commons.rules;

import commons.game.PieceName;
import commons.game.Board;
import commons.game.Color;
import commons.game.Piece;
import commons.game.Position;

import java.util.ArrayList;
import java.util.Arrays;

/**Note about this implementation of the rule:
 * On a normal game, there are two different castling rules, the long one, and the short one.
 * For the long one to be valid we need: <br>
 * &emsp;* A king and, on the same line, a rook at a distance of four (4) squares <br>
 * For the short one:<br>
 * &emsp;* A king and, on the same line, a rook at a distance of three (3) squares<br>
 * In both cases:<br>
 * &emsp;* The king cannot be checked<br>
 * &emsp;* None of the squares the king goes through can be menaced by an opposing piece<br>
 * &emsp;* The king ALWAYS moves two (2) squares to the side.<br>
 * &emsp;* The king and the rook must have been the whole game at the same position*/
public class LongCastlingRule implements SpecialRule{

    private final MovementRule[] movementRules;
    private RestrictionRule[] restrictionRules;
    public LongCastlingRule(MovementRule[] movementRules, RestrictionRule[] restrictionRules) {
        this.movementRules = movementRules;
        this.restrictionRules = Arrays.copyOf(restrictionRules, restrictionRules.length);
        // From our standpoint (Blacks), long castling is to the left ALWAYS!.
        this.restrictionRules = putNewRestriction(this.restrictionRules, new HorizontalMaxQuantityRule(1), new HorizontalSideMaxQtyRule(2, 1));
    }
    // current position, piece the king is at the start of the movement
    @Override
    public boolean specialRuleIsActive(Position currentPosition, ArrayList<Board> historyOfBoards) {
        Board currentBoard = historyOfBoards.get(historyOfBoards.size() - 1);
        Piece king = currentBoard.getPiece(currentPosition);
        Position posibleRightRook = new Position(currentPosition.getRow(), currentPosition.getCol()+4);
        Position posibleLeftRook = new Position(currentPosition.getRow(), currentPosition.getCol()-4);
        Position[] possiblePieces = new Position[]{posibleLeftRook, posibleRightRook};

        int rookAtPos = typeOfPieceAtPositions(possiblePieces, king.getName(), king.getColor(), historyOfBoards);
        if(rookAtPos == -1)
            return false;
        if(!hasBeenAtTheSamePositionAllTheGame(possiblePieces[rookAtPos], historyOfBoards))
            return false;

        return hasBeenAtTheSamePositionAllTheGame(currentPosition, historyOfBoards);
    }


    public int typeOfPieceAtPositions(Position[] pieces, PieceName pieceName, Color color, ArrayList<Board> historyOfBoards){
        Board currentBoard = historyOfBoards.get(historyOfBoards.size() - 1);

        int i = 0;
        for (Position pos : pieces){
            Piece piece = currentBoard.getPiece(pos);
            if(piece == null)
                continue;
            if(posIsInBoundsAtX(pos, currentBoard)){
                continue; // I assume that it will always be valid at the Y axis
            }
            if(piece.getName() == pieceName && piece.getColor() == color)
                return i;
            i++;
        }
        return -1;
    }

    public boolean posIsInBoundsAtX(Position pos, Board board){
        return pos.getCol() >= 0 && pos.getCol() <= board.getWidth() - 1;
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
        return new MovementRule[0];
    }

    @Override
    public RestrictionRule[] getRestrictionRules() {
        return new RestrictionRule[0];
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

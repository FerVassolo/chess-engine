package commons.rules;

import commons.*;

import java.util.ArrayList;
import java.util.Map;

public class OnPassant implements SpecialRule{

    private MovementRule[] movementRules;
    private RestrictionRule[] restrictionRules;

    // Lo hago estatico, ¿total siempre son las mismas reglas?
    public OnPassant(MovementRule[] movementRules){
        //todo
        this.movementRules = movementRules;
        // If we reached here then we know that the diagonal move is valid without having to verify if there is a piece next to it
        this.restrictionRules = new RestrictionRule[]{new StraightMaxQuantityRule(1), new CannotCaptureVertically(), new OnlyForwardMovementIsValid(), new DiagonalMaxQuantityRule(1)};
    }

    // The pawn had to be on the same position all the game through;
    @Override
    public boolean specialRuleIsActive(Position currentPosition, ArrayList<Board> historyOfBoards) {
        return false;
    }

    // PAJA DE SEGUIR.
    public boolean lastMoveWasDoublePawnMovement(Position currentPosition, ArrayList<Board> historyOfBoards){
        int length = historyOfBoards.size();
        Board lastBoard = historyOfBoards.get(length-1);
        Board previousToLastBoard = historyOfBoards.get(length-2);
        Map<Position, Piece> positions = lastBoard.getPositions();
        for(Position pos: lastBoard.getPositionsMapKeys()){
            Piece piece = positions.get(pos);
            if(piece.getName() == PieceName.PAWN){
                if(piece.getColor() == Color.WHITE){
                    if(previousToLastBoard.getPieceByVector(pos.getRow()+2, pos.getCol()) == piece){
                        return true;
                    }
                }
                else if(piece.getColor() == Color.BLACK){
                    if(previousToLastBoard.getPieceByVector(pos.getRow()+2, pos.getCol()) == piece){
                        return true;
                    }
                }
            }
        }
        return true;
    }

    // THis is actually for the castling and double move, not for the on passant.
    public boolean pieceNeverMoved(Position currentPosition, ArrayList<Board> historyOfBoards){
        for(int i = 0; i < historyOfBoards.size() -1; i++){
            Board prevBoard = historyOfBoards.get(i);
            Board newBoard = historyOfBoards.get(i+1);
            Position oldPos = prevBoard.getPosByPos(currentPosition);
            Position newPos = newBoard.getPosByPos(currentPosition);
            if(prevBoard.getPiece(oldPos) != newBoard.getPiece(newPos)){
                return false;
            }
        }
        return true;
    }

    @Override
    public MovementRule[] getMovementRules() {
        return movementRules;
    }

    @Override
    public RestrictionRule[] getRestrictionRules() {
        return restrictionRules;
    }
}

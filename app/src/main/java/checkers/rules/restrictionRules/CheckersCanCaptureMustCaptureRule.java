package checkers.rules.restrictionRules;

import commons.board.Board;
import commons.board.Position;
import commons.movement.MovementValidator;
import commons.piece.Piece;
import commons.rules.restrictionRules.RestrictionRule;

import java.util.ArrayList;
import java.util.List;

// If a player can eat but doesn't this rule won't let they
public class CheckersCanCaptureMustCaptureRule implements RestrictionRule {

    //usar DiagonalCheckersCapture para validar cada pieza si puede comer.

    // si el movimiento hecho por el player ya es un capture, entonces esta regla ni se pide:
    // basicamente: si el movimiento es mayor igual a 2
    // caso contrario, devuelve false;
    @Override
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        int distance = Math.abs(pieceNewPos.getRow() - pieceOriginalPos.getRow());
        if(distance > 1)
            return true;
        Piece movingPiece = board.getPiece(pieceOriginalPos);
        ArrayList<Position> colorPieces = board.getAllPositionsOfColor(movingPiece.getColor());

        for(Position pos : colorPieces){
            if(pieceCanCapture(pos, board)){
                return false;
            }
        }
        return true;
    }

    // en checkers no existen game rules q limiten esto, así que no me importan las game rules.
    // ¿¿agarro los piece possible moves??
    public boolean pieceCanCapture(Position pos, Board board){
        // busca piezas en sus límites laterales.
        List<Position> limitingPositions = limitsWithAnOppositePiece(pos, board);
        if(limitingPositions.size() == 0)
            return false;
        else{
            for(Position limitingPos : limitingPositions){
                if(canCapturePiece(pos, limitingPos, board))
                    return true;
            }
        }
        return false;
    }

    public List<Position> limitsWithAnOppositePiece(Position pos, Board board){
        List<Position> limitingPositions = getLimitingPositions(pos, board);
        List<Position> oppositeLimits = new ArrayList<>();
        for(Position limitPos : limitingPositions){
            if(posAreDifferentColor(pos, limitPos, board))
                oppositeLimits.add(limitPos);
        }
        return oppositeLimits;
    }


    // we assume that a piece can capture if it can jump over an opposite limiting piece, otherwise it becomes unfathomable.
    public boolean canCapturePiece(Position pos, Position oppositePos, Board board){
        Piece piece = board.getPiece(pos);
        int rowDistance = oppositePos.getRow() - pos.getRow();
        int colDistance = oppositePos.getCol() - pos.getCol();

        // I've made the maths, believe me, the calculations are right for straight and diagonal movement:
        Position capturingPos = new Position(oppositePos.getRow() + rowDistance, oppositePos.getCol() + colDistance);
        if(!posInBounds(capturingPos, board))
            return false;
        return new MovementValidator().isValid(piece.getMovementRules(), piece.getRestrictionRules(), pos, capturingPos, board);
    }

    public boolean posInBounds(Position pos, Board board){
        int height = board.getHeight();
        int width = board.getWidth();
        int row = pos.getRow();
        int col = pos.getCol();
        if(row >= height || row < 0 || col < 0 || col >= width)
            return false;
        return true;
    }
    public List<Position> getLimitingPositions(Position pos, Board board){
        int width = board.getWidth();
        int height = board.getHeight();
        int col = pos.getCol();
        int row = pos.getRow();
        List<Position> positions = new ArrayList<>();
        int[] directions = new int[]{-1, 0, 1};

        for(int hDirection : directions){
            if(row + hDirection >= height || row + hDirection < 0)
                continue;
            for(int wDirection : directions){
                if(col + wDirection >= width || col + wDirection < 0)
                    continue;
                positions.add(new Position(row + hDirection, col + wDirection));
            }
        }
        return positions;
    }

    public boolean posAreDifferentColor(Position pos, Position otherPos, Board board){
        if(board.getPiece(otherPos) == null)
            return false;
        return board.getPiece(otherPos).getColor() != board.getPiece(pos).getColor();
    }

    @Override
    public String errorMessage() {
        return "You are obliged to capture.";
    }


}

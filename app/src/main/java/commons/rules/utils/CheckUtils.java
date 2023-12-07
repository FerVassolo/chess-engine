package commons.rules.utils;

import commons.board.Board;
import commons.board.Position;
import commons.movement.MovementValidator;
import commons.piece.Piece;
import commons.piece.PieceName;
import commons.game.*;

import java.io.PrintStream;

public class CheckUtils {

    private PrintStream originalOut;

    /**Returns False if the color IS in check*/
    public boolean verifyBoardIsNotInCheck(Board board, Color color){

        Position kingPos = getKingPos(board, color);
        for(Position pos : board.getPositionsMapKeys()) {
            if(pieceIsMenacingPos(pos, kingPos, board, color)){
                System.out.println(color + " king is in check");
                return false;
            }
        }
        return true;
    }

    public boolean pieceIsMenacingPos(Position currentPos, Position newPos, Board board, Color color){
        Piece piece = board.getPiece(currentPos);
        if(!pieceIsOfColor(piece, color) && piece != null){
            if(pieceCanAttackPos(currentPos, newPos, board)){System.out.println("King is menaced by: " + board.getPiece(currentPos));}
            return pieceCanAttackPos(currentPos, newPos, board);
        }
        return false;
    }

    public Position getKingPos(Board board, Color color){
        for(Position pos : board.getPositionsMapKeys()){
            Piece piece = board.getPiece(pos);
            if(piece != null && piece.getColor() == color && piece.getName() == PieceName.KING){
                return pos;
            }
        }
        return null; // Should never reach here.
    }

    public boolean pieceIsOfColor(Piece piece, Color color){
        if(piece == null) return false;
        return piece.getColor() == color;
    }


    private boolean pieceCanAttackPos(Position piecePos, Position targetPos, Board board) {
        Piece piece = board.getPiece(piecePos);
        //suppressStandardOutput(); // If a system.out.println is called it won't appear on the console.
        boolean result = new MovementValidator().isValid(piece.getMovementRules(), piece.getRestrictionRules(), piecePos, targetPos, board);// NO ESTÁ TENIENDO EN CUENTA LOS GAME RULES??
        /*restoreStandardOutput(); // any system.out.println can now appear.*/
        return result;
    }

    /**All the Restriction Methods have a System.out.println(Restriction Message).
     * I don't want them to be printed when checking if there is check.*/
    public void suppressStandardOutput() {
        // Store the original System.out print stream
        originalOut = System.out;
        // Create a new PrintStream that discards the output
        PrintStream nullPrintStream = new PrintStream(new NullOutputStream());
        // Redirect the System.out to the nullPrintStream
        System.setOut(nullPrintStream);
    }

    public void restoreStandardOutput() {
        // Restore the original System.out print stream
        System.setOut(originalOut);
    }



}

package commons.rules;


import commons.Board;
import commons.Position;

// The difference between promotion rules is that they do not return true or false.
// They should also return the piece promoted and the piece it is promoted to.
// ¿Had promotion after making the move?
public interface PromotionRule {


    public boolean validatePromotion(Position pieceOriginalPos, Position pieceNewPos, Board board);

    public Board promotedBoard(Position pieceOriginalPos, Position pieceNewPos, Board board);
}

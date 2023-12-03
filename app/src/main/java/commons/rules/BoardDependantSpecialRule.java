package commons.rules;


import commons.game.Board;
import commons.game.Position;

// The difference between promotion rules is that they do not return true or false.
// They should also return the piece promoted and the piece it is promoted to.
// ¿Had promotion after making the move?


// This one is used, for example, for promotions and capturing in checkers
public interface BoardDependantSpecialRule {

    public boolean ruleIsActive(Position pieceOriginalPos, Position pieceNewPos, Board board);

    public Board modifiedBoard(Position pieceOriginalPos, Position pieceNewPos, Board board);
}

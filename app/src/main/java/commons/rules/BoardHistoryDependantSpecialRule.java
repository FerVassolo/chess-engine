package commons.rules;

import commons.game.Board;
import commons.game.Position;

import java.util.ArrayList;

public interface BoardHistoryDependantSpecialRule {

    public boolean ruleIsActive(Position pieceOriginalPos, Position pieceNewPos, ArrayList<Board> historyOfBoards);

    public Board modifiedBoard(Position pieceOriginalPos, Position pieceNewPos, Board board);
}

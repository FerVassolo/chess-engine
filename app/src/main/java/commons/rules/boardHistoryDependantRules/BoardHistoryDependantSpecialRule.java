package commons.rules.boardHistoryDependantRules;

import commons.board.Board;
import commons.board.Position;

import java.util.ArrayList;

public interface BoardHistoryDependantSpecialRule {

    public boolean ruleIsActive(Position pieceOriginalPos, Position pieceNewPos, ArrayList<Board> historyOfBoards);

    public Board modifiedBoard(Position pieceOriginalPos, Position pieceNewPos, Board board);
}

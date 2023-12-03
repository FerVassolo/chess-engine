package commons.game;

import commons.rules.BoardHistoryDependantSpecialRule;
import commons.rules.BoardDependantSpecialRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Movement {

    public Board makeMove(Game game, Board board, Position oldPos, Position newPos) {
        boolean isValid = new MovementValidator().validateMovement(game, board, oldPos, newPos);

        if (isValid) {
            return movePiece(board, oldPos, newPos, game.getHistoryOfBoards());
        }
        return board;
    }

    public Board movePiece(Board board, Position oldPos, Position newPos) {
        Map<Position, Piece> newPosDisplay = new HashMap<>(board.getPositions());
        Piece movedPiece = newPosDisplay.get(board.getPosByPos(oldPos));

        if (boardDependantIsValid(board, oldPos, newPos)) {
            return boardDependantModifiedBoard(board, oldPos, newPos);
        }

        newPosDisplay.put(board.getPosByPos(oldPos), null);
        newPosDisplay.put(board.getPosByPos(newPos), movedPiece);

        return new Board(newPosDisplay, board.getHeight(), board.getWidth());
    }


    public Board movePiece(Board board, Position oldPos, Position newPos, ArrayList<Board> historyOfBoards) {
        Map<Position, Piece> newPosDisplay = new HashMap<>(board.getPositions());
        Piece movedPiece = newPosDisplay.get(board.getPosByPos(oldPos));

        if (boardDependantIsValid(board, oldPos, newPos)) {
            return boardDependantModifiedBoard(board, oldPos, newPos);
        }
        if (hasGameModifyingRule(historyOfBoards, oldPos, newPos)){
            return historyDependantModifiedBoard(historyOfBoards, oldPos, newPos);
        }
        newPosDisplay.put(board.getPosByPos(oldPos), null);
        newPosDisplay.put(board.getPosByPos(newPos), movedPiece);

        return new Board(newPosDisplay, board.getHeight(), board.getWidth());
    }

    private boolean boardDependantIsValid(Board board, Position oldPos, Position newPos) {
        Piece movedPiece = board.getPiece(oldPos);

        for (BoardDependantSpecialRule rule : movedPiece.getBoardDependantRules()) {
            if (rule.ruleIsActive(oldPos, newPos, board)) {
                System.out.println("Rule is active");
                return true;
            }
        }
        System.out.println("Rule is not active");
        return false;
    }

    /**¿What if more than one DependantRule is active? after calling the
     board dependant rules, we overlap the boards, where a Queen has priority of a pawn, and a null from a piece.
    <br>So, null > queen == king (or any) > pawn == man.*/
    private Board boardDependantModifiedBoard(Board board, Position oldPos, Position newPos) {
        Piece movedPiece = board.getPiece(oldPos);
        List<Board> modifiedBoards = new ArrayList<>();
        for (BoardDependantSpecialRule rule : movedPiece.getBoardDependantRules()) {
            if (rule.ruleIsActive(oldPos, newPos, board)) {
                modifiedBoards.add(rule.modifiedBoard(oldPos, newPos, board));
                return rule.modifiedBoard(oldPos, newPos, board);
            }
        }
        return null;
    }

    private Board appendedBoards(List<Board> modifiedBoards, Position[] path){
        List<Position> conflictPositions = new ArrayList<>();


        return null;
    }

    private boolean hasGameModifyingRule(ArrayList<Board> historyOfBoards, Position oldPos, Position newPos){
        Board lastBoard = historyOfBoards.get(historyOfBoards.size() - 1);
        Piece movedPiece = lastBoard.getPiece(oldPos);

        for (BoardHistoryDependantSpecialRule rule : movedPiece.getDependantSpecialRules()) {
            if (rule.ruleIsActive(oldPos, newPos, historyOfBoards)) {
                return true;
            }
        }
        return false;
    }

    private Board historyDependantModifiedBoard(ArrayList<Board> historyOfBoards, Position oldPos, Position newPos){
        Board lastBoard = historyOfBoards.get(historyOfBoards.size() - 1);
        Piece movedPiece = lastBoard.getPiece(oldPos);

        for (BoardHistoryDependantSpecialRule rule : movedPiece.getDependantSpecialRules()) {
            if (rule.ruleIsActive(oldPos, newPos, historyOfBoards)) {
                return rule.modifiedBoard(oldPos, newPos, lastBoard);
            }
        }
        return null; // Should never reach here.
    }


    public ArrayList<Position> getPieceAllPossibleMoves(Position piecePos, Board board, Game game) {
        ArrayList<Position> array = new ArrayList<>();
        Game newGame = new Game(game);

        if (game.getCurrentPlayer().getColor() != board.getPiece(piecePos).getColor()) {
            newGame.passTurn();
        }

        for (Position pos : board.getPositionsMapKeys()) {
            if (new MovementValidator().validateMovement(newGame, board, piecePos, pos)) {
                array.add(pos);
            }
        }

        return array;
    }
}

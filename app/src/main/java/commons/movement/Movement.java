package commons.movement;

import commons.board.Board;
import commons.game.Game;
import commons.board.Position;
import commons.piece.Piece;
import commons.piece.PieceComparator;
import commons.piece.PieceName;
import commons.rules.boardHistoryDependantRules.BoardHistoryDependantSpecialRule;
import commons.rules.boardDependantRules.BoardDependantSpecialRule;
import commons.rules.movementRules.MovementRule;

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
                return true;
            }
        }
        return false;
    }

    /**What if more than one DependantRule is active? after calling the
     board dependant rules, we overlap the boards, where a Queen has priority of a pawn, and a null from any piece.
    <br>So, null > queen == king (or any other, except pawn and man) > pawn == man.*/
    private Board boardDependantModifiedBoard(Board board, Position oldPos, Position newPos) {
        Piece movedPiece = board.getPiece(oldPos);
        Position[] path = getPath(board, oldPos, newPos);
        List<Board> modifiedBoards = new ArrayList<>();
        for (BoardDependantSpecialRule rule : movedPiece.getBoardDependantRules()) {
            if (rule.ruleIsActive(oldPos, newPos, board)) {
                modifiedBoards.add(rule.modifiedBoard(oldPos, newPos, board));
            }
        }
        if(modifiedBoards.size() == 1)
            return modifiedBoards.get(0);
        else
            return appendedBoards(modifiedBoards, path);
    }

    private Board appendedBoards(List<Board> modifiedBoards, Position[] conflictedPath){
        PieceComparator comparator = new PieceComparator();
        Board firstBoard = modifiedBoards.get(0);
        Map<Position, Piece> appendedDisplay = new HashMap<>(firstBoard.getPositions());

        for(Position pos: conflictedPath){
            Piece firstPiece = firstBoard.getPiece(pos);
            if(firstPiece == null)
                continue;
            PieceName name = firstBoard.getPiece(pos).getName();

            for (int i = 1; i < modifiedBoards.size(); i++) {
                Board board = modifiedBoards.get(i);
                Piece comparedPiece = board.getPiece(pos);
                if(comparedPiece == null){
                    appendedDisplay.put(firstBoard.getPosByPos(pos), null);
                    break;
                }
                PieceName comparingName = board.getPiece(pos).getName();
                if(comparator.compare(name, comparingName) < 0){
                    appendedDisplay.put(firstBoard.getPosByPos(pos), comparedPiece);
                    break;
                }
            }
        }
        return new Board(appendedDisplay, firstBoard.getHeight(), firstBoard.getWidth());
    }

    private Position[] getPath(Board board, Position oldPos, Position newPos){
        Piece movedPiece = board.getPiece(oldPos);

        for(MovementRule rule : movedPiece.getMovementRules()){
            if(rule.validateMovement(oldPos, newPos))
                return rule.getPath(oldPos, newPos);
        }
        return new Position[]{};
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

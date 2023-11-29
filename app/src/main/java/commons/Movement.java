package commons;

import chess.rules.PromotionRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Movement {

    public Board makeMove(Game game, Board board, Position oldPos, Position newPos) {
        boolean isValid = new MovementValidator().validateMovement(game, board, oldPos, newPos);

        if (isValid) {
            return movePiece(board, oldPos, newPos);
        }
        return board;
    }

    public Board movePiece(Board board, Position oldPos, Position newPos) {
        Map<Position, Piece> newPosDisplay = new HashMap<>(board.getPositions());
        Piece movedPiece = newPosDisplay.get(board.getPosByPos(oldPos));

        if (canPromote(board, oldPos, newPos)) {
            return promotedBoard(board, oldPos, newPos);
        }

        newPosDisplay.put(board.getPosByPos(oldPos), null);
        newPosDisplay.put(board.getPosByPos(newPos), movedPiece);

        return new Board(newPosDisplay, board.getHeight(), board.getWidth());
    }

    private boolean canPromote(Board board, Position oldPos, Position newPos) {
        Piece movedPiece = board.getPiece(oldPos);

        for (PromotionRule rule : movedPiece.getPromotionRules()) {
            if (rule.validatePromotion(oldPos, newPos, board)) {
                return true;
            }
        }
        return false;
    }

    private Board promotedBoard(Board board, Position oldPos, Position newPos) {
        Piece movedPiece = board.getPiece(oldPos);

        for (PromotionRule rule : movedPiece.getPromotionRules()) {
            if (rule.validatePromotion(oldPos, newPos, board)) {
                return rule.promotedBoard(oldPos, newPos, board);
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

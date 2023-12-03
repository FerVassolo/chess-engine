package commons.rules;

import commons.game.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CastlingRule implements BoardHistoryDependantSpecialRule {

    // king must not be at check. done
    // king must've moved two places horizontally.
    // no interposing pieces. done
    // same pos the whole game. done


    // CASTLING IS PERFECT, BUT THE UI IS NOT GETTING IT.
    @Override
    public boolean ruleIsActive(Position pieceOriginalPos, Position pieceNewPos, ArrayList<Board> historyOfBoards) {
        System.out.println("Verifying castling rule");
        Board currentBoard = historyOfBoards.get(historyOfBoards.size() - 1);
        Piece king = currentBoard.getPiece(pieceOriginalPos);

        if(!isHorizontal(pieceOriginalPos, pieceNewPos))
            return false;
        if(!new CheckUtils().verifyBoardIsNotInCheck(currentBoard, king.getColor()))
            return false;
        if(Math.abs(pieceNewPos.getCol() - pieceOriginalPos.getCol()) != 2)
            return false;
        if(king.getName() != PieceName.KING)
            return false;

        int kingDirection = pieceNewPos.getCol() > pieceOriginalPos.getCol() ? 1 : -1; // 1: right, 2: left
        Position[] possiblePieces = possibleRookPosition(pieceOriginalPos, kingDirection);

        List<Integer> rookAtPos = typeOfPieceAtPositions(possiblePieces, PieceName.ROOK, king.getColor(), historyOfBoards);
        if (rookAtPos.isEmpty()) {
            return false;
        } else {
            for (Integer i : rookAtPos) {
                if(!new PieceInterposesHorizontallyRestriction().validateRule(pieceOriginalPos, possiblePieces[i], currentBoard)){
                    System.out.println("Castling not valid because interposing pos");
                    return false;
                }
                if (rookIsValidMove(pieceOriginalPos, possiblePieces[i], currentBoard, historyOfBoards)) {
                    System.out.println("CASTLING IS VALID");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean rookIsValidMove(Position kingPos, Position rookPos, Board currentBoard, ArrayList<Board> historyOfBoards) {
        return hasBeenAtTheSamePositionAllTheGame(rookPos, historyOfBoards) &&
                new PieceInterposesHorizontallyRestriction().validateRule(kingPos, rookPos, currentBoard);
    }

    @Override
    public Board modifiedBoard(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        int kingDirection = pieceNewPos.getCol() > pieceOriginalPos.getCol() ? 1 : -1; // 1: right, -1: left
        Position[] possiblePieces = possibleRookPosition(pieceOriginalPos, kingDirection);
        Position rookNewPos = new Position(pieceNewPos.getRow(), pieceNewPos.getCol() - kingDirection);

        Map<Position, Piece> newPosDisplay = new HashMap<>(board.getPositions());
        Piece movedPiece = newPosDisplay.get(board.getPosByPos(pieceOriginalPos));
        System.out.println("POSSIBLE PIECES: " + possiblePieces);

        for(Position pos: possiblePieces){
            Piece rook = newPosDisplay.get(board.getPosByPos(pos));
            System.out.println("rook: " + rook);
            if(rook != null){
                System.out.println("Modifying rook positon");
                newPosDisplay.put(board.getPosByPos(pos), null);
                newPosDisplay.put(board.getPosByPos(rookNewPos), rook);
            }
        }

        newPosDisplay.put(board.getPosByPos(pieceOriginalPos), null);
        newPosDisplay.put(board.getPosByPos(pieceNewPos), movedPiece);

        return new Board(newPosDisplay, board.getHeight(), board.getWidth());
    }

    private Position[] possibleRookPosition(Position pieceOriginalPos, int direction) {
        int col = pieceOriginalPos.getCol();
        int row = pieceOriginalPos.getRow();
        if(direction == -1)
            return new Position[]{new Position(row, col - 4), new Position(row, col - 3)};
        else
            return new Position[]{ new Position(row, col + 4), new Position(row, col + 3)};
    }

    private List<Integer> typeOfPieceAtPositions(Position[] pieces, PieceName pieceName, Color color, ArrayList<Board> historyOfBoards) {
        Board currentBoard = historyOfBoards.get(historyOfBoards.size() - 1);
        List<Integer> positionsThatApply = new ArrayList<>();

        for (int i = 0; i < pieces.length; i++) {
            Position pos = pieces[i];
            Piece piece = currentBoard.getPiece(pos);

            if (piece == null)
                continue;

            if(!posIsInBoundsAtX(pos, currentBoard)){
                System.out.println("outabounds");
                continue;
            }
            if (piece.getName() == pieceName && piece.getColor() == color) {
                positionsThatApply.add(i);
            }
        }
        return positionsThatApply;
    }

    private boolean posIsInBoundsAtX(Position pos, Board board) {
        return pos.getCol() >= 0 && pos.getCol() <= board.getWidth() - 1;
    }

    private boolean hasBeenAtTheSamePositionAllTheGame(Position currentPosition, ArrayList<Board> historyOfBoards) {
        Board firstBoard = historyOfBoards.get(0);
        Piece firstPiece = firstBoard.getPiece(currentPosition);

        return historyOfBoards.stream()
                .map(board -> board.getPiece(currentPosition))
                .allMatch(piece -> piece != null && piece.equals(firstPiece));
    }
    public boolean isHorizontal(Position pieceOriginalPos, Position pieceNewPos) {
        return new HorizontalMovement().validateMovement(pieceOriginalPos, pieceNewPos);
    }
}

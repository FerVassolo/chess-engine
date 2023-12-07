package commons.rules.boardDependantRules;


import commons.board.Board;
import commons.board.Position;
import commons.piece.Piece;
import commons.rules.movementRules.DiagonalMovement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// the amount of jumps is already taken care by DiagonalMaxQty
// Actually it is checkers normal movement for all pieces.

public class ComplexDiagonalMustCapture implements BoardDependantSpecialRule {

    // to capture, no matter the distance, squares should be:
    // piece -> empty -> piece of other color -> empty -> piece of other color -> empty -> ...


    // MODULARIZE
    // ALSO, if distance is 4, 6, 8... It should make a tree to se if to any side there is a piece, and for that piece or two look for a null,
    // and to that null (or four) other pieces and so on...


    //CAMBIAR TRUE POR FALSES, ESTO ES UNA BOARD DEPENDANT RULE!!, todo Por cada movimiento verificar que vaya acorde a las restriction rules igual...
    // todo la diagonal move queda, pero para max 1. Luego esta es para 2 y más...
    @Override
    public boolean ruleIsActive(Position pieceOriginalPos, Position pieceNewPos, Board board) {

        //rule doesn't apply cause there is no possibility of capture
        if(distanceIsOneOrCero(pieceOriginalPos, pieceNewPos)){
            System.out.println("Distance == 1 or 0");
            return false;
        }

        if(!distanceIsEven(pieceOriginalPos, pieceNewPos)){
            System.out.println("Distance Not even");
            return false;
        }

        List<List<Position>> capturingPaths = getPieceCapturingPaths(pieceOriginalPos, board, new ArrayList<>(), new ArrayList<>(), null);

        List<Position> usedPath = getUsedPath(pieceOriginalPos, pieceNewPos, capturingPaths);
        System.out.println(usedPath.isEmpty());
        return !usedPath.isEmpty();
    }

    @Override
    public Board modifiedBoard(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        List<List<Position>> capturingPaths = getPieceCapturingPaths(pieceOriginalPos, board, new ArrayList<>(), new ArrayList<>(), null);
        List<Position> usedPath = getUsedPath(pieceOriginalPos, pieceNewPos, capturingPaths);
        Map<Position, Piece> newPosDisplay = new HashMap<>(board.getPositions());
        Piece movedPiece = newPosDisplay.get(board.getPosByPos(pieceOriginalPos));

        for(Position pos : usedPath){
            newPosDisplay.put(board.getPosByPos(pos), null);
        }

        newPosDisplay.put(board.getPosByPos(pieceNewPos), movedPiece);
        return new Board(newPosDisplay, board.getHeight(), board.getWidth());
    }

    public List<Position> getUsedPath(Position pieceOriginalPos, Position pieceNewPos, List<List<Position>> capturingPaths){
        for(List<Position> path : capturingPaths) {
            System.out.println("Path open: " + path.get(0).getRow() + ", " +path.get(0).getCol());
            System.out.println("Path finish: " + path.get(path.size() - 1).getRow() + ", " +path.get(path.size() - 1).getCol());
            if (path.get(0).equals(pieceOriginalPos) && path.get(path.size() - 1).equals(pieceNewPos))
                return path;
        }
        return new ArrayList<>();
    }

    public boolean distanceIsOneOrCero(Position pieceOriginalPos, Position pieceNewPos){
        int rowDistance = Math.abs(pieceNewPos.getRow() - pieceOriginalPos.getRow());
        int colDistance = Math.abs(pieceNewPos.getCol() - pieceOriginalPos.getCol());
        if((rowDistance == 1 && colDistance == 1) || (rowDistance == 0 && colDistance == 0))
            return true;
        return false;
    }
    public boolean distanceIsEven(Position pieceOriginalPos, Position pieceNewPos){
        int rowDistance = Math.abs(pieceNewPos.getRow() - pieceOriginalPos.getRow());
        int colDistance = Math.abs(pieceNewPos.getCol() - pieceOriginalPos.getCol());
        if(rowDistance % 2 != 0 || colDistance % 2 != 0)
            return false;
        return true;
    }


    private boolean canMoveThere(Position pieceOriginalPos, Position to, boolean shouldBeEmpty, Board board){
        Piece piece = board.getPiece(to);
        if((piece != null && (shouldBeEmpty || (piece.getColor() == board.getPiece(pieceOriginalPos).getColor())))|| (piece == null && !shouldBeEmpty)){
            return false;
        }
        return true;
    }


    // As all movements in checkers are diagonal, but this will make it extensible to other game modes
    public boolean isDiagonal(Position pieceOriginalPos, Position pieceNewPos){
        return new DiagonalMovement().validateMovement(pieceOriginalPos, pieceNewPos);
    }

    private List<List<Position>> getPieceCapturingPaths(Position pos, Board board, List<List<Position>> certainPaths, List<Position> currentPath, Position comesFromPos) {
        List<Position> possibleCapturingPos = new ArrayList<>(getVertexesWithPiece(pos, board));

        // Remove the position the piece came from
        possibleCapturingPos.remove(comesFromPos);


        if (currentPath.isEmpty()) {
            currentPath.add(pos);
        }
        System.out.println("called");
        for (Position p : possibleCapturingPos) {

            if (canDiagonallyCapturePos(pos, p, board)) {
                Position finalMovePos = piecePosAfterEating(pos, p);
                System.out.println(finalMovePos.getRow() + ", " +finalMovePos.getCol());
                List<Position> newPath = new ArrayList<>(currentPath); // Create a new path
                newPath.add(p);
                newPath.add(finalMovePos);

                certainPaths.add(newPath);
                getPieceCapturingPaths(finalMovePos, board, certainPaths, newPath, p);
            }
        }
        return certainPaths;
    }

    private List<Position> getVertexesWithPiece(Position pos, Board board){
        List<Position> piecesPos = new ArrayList<>();
        int row = pos.getRow();
        int col = pos.getCol();
        Position[] tryiedPositions = new Position[]{
                new Position(row - 1, col - 1),
                new Position(row + 1, col - 1),
                new Position(row - 1, col + 1),
                new Position(row + 1, col + 1)
        };
        for(Position p : tryiedPositions) {
            if (board.getPiece(p) != null)
                piecesPos.add(p);
        }
        return piecesPos;
    }

    // nooo bolas, ¡¡no me interesa el movement validator!! Solo si puedo mover diagonal y caer en un null está bien. Obvio q me importa el color y el outabouds tho...
    public boolean canDiagonallyCapturePos(Position originalPos, Position newPos, Board board){
        Position movingToPos = piecePosAfterEating(originalPos, newPos);
        if(!posInBounds(movingToPos, board))
            return false;
        Piece originalPiece = board.getPiece(originalPos);
        System.out.println("Original: " + originalPos.getRow() + ", " + originalPos.getCol());
        Piece newPiece = board.getPiece(newPos);
/*        if(newPiece == null || newPiece.getColor() == originalPiece.getColor())
            return false;*/
        return canMoveThere(originalPos, movingToPos,true, board);
    }

    public Position piecePosAfterEating(Position originalPos, Position newPos){
        int rowDistance = newPos.getRow() - originalPos.getRow();
        int colDistance = newPos.getCol() - originalPos.getCol();
        return new Position(newPos.getRow() + rowDistance, newPos.getCol() + colDistance);
    }

    public boolean posInBounds(Position pos, Board board){
        int row = pos.getRow();
        int col = pos.getCol();
        return row >= 0 && col >= 0 && col < board.getWidth() && row < board.getHeight();
    }

}

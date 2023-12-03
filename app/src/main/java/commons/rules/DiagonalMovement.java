package commons.rules;

import commons.game.Position;

public class DiagonalMovement implements MovementRule{


    @Override
    public boolean validateMovement(Position currentPosition, Position newPosition) {
        int[] currentPos = new int[]{currentPosition.getRow(), currentPosition.getCol()};
        int[] newPos = new int[]{newPosition.getRow(), newPosition.getCol()};
        int xAxis = Math.abs(currentPos[0] - newPos[0]);
        int yAxis = Math.abs(currentPos[1] - newPos[1]);
        return xAxis == yAxis;
    }

    @Override
    public Position[] getPath(Position pieceOriginalPos, Position pieceNewPos) {
        int rowDirection = pieceNewPos.getRow() > pieceOriginalPos.getRow() ? 1 : -1; // 1: up, -1: down
        int colDirection = pieceNewPos.getCol() > pieceOriginalPos.getCol() ? 1 : -1; // 1: right, -1: left
        int distance = Math.abs(pieceNewPos.getRow() - pieceOriginalPos.getRow());
        int steps = Math.abs(distance);

        Position[] diagonalPath = new Position[steps];
        int currentRow = pieceOriginalPos.getRow();
        int currentCol = pieceOriginalPos.getCol();

        // Generate the positions passed through
        for (int i = 0; i < steps; i++) {
            currentRow += rowDirection;
            currentCol += colDirection;
            diagonalPath[i] = new Position(currentRow, currentCol);
        }
        return diagonalPath;
    }
}

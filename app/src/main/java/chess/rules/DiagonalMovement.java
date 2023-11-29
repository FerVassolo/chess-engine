package chess.rules;

import commons.Position;

public class DiagonalMovement implements MovementRule{


    @Override
    public boolean validateMovement(Position currentPosition, Position newPosition) {
        int[] currentPos = new int[]{currentPosition.getRow(), currentPosition.getCol()};
        int[] newPos = new int[]{newPosition.getRow(), newPosition.getCol()};
        int xAxis = Math.abs(currentPos[0] - newPos[0]);
        int yAxis = Math.abs(currentPos[1] - newPos[1]);
        return xAxis == yAxis;
    }
}

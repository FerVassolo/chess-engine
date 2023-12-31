package commons.rules.movementRules;

import commons.board.Position;

public class HorizontalMovement implements MovementRule {

    @Override
    public boolean validateMovement(Position currentPosition, Position newPosition) {
        int oldRow = currentPosition.getRow();
        int newRow = newPosition.getRow();
        return oldRow - newRow == 0;
    }

    @Override
    public Position[] getPath(Position currentPosition, Position newPosition) {
        int direction = getDirection(currentPosition, newPosition);
        int distance = getDistance(currentPosition, newPosition);
        int steps = Math.abs(distance);
        int currentCol = currentPosition.getCol();
        int row = currentPosition.getRow();

        Position[] path = new Position[steps + 1];
        path[0] = currentPosition;
        for (int i = 1; i < steps + 1; i++) {
            currentCol += direction;
            path[i] = new Position(row, currentCol);
        }
        return path;
    }

    public int getDirection(Position currentPosition, Position newPosition){
        return currentPosition.getCol() > newPosition.getCol() ? 1 : -1; // 1: right, -1: left;
    }

    public int getDistance(Position currentPosition, Position newPosition){
        return Math.abs(currentPosition.getCol() - newPosition.getCol());
    }


}

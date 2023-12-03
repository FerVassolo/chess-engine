package commons.rules;

import commons.game.Position;

public class VerticalMovement implements MovementRule{

    @Override
    public boolean validateMovement(Position currentPosition, Position newPosition) {
        int oldCol = currentPosition.getCol();
        int newCol = newPosition.getCol();
        return oldCol - newCol == 0; //=0 --> true, !=0 false
    }
    @Override
    public Position[] getPath(Position currentPosition, Position newPosition) {
        int direction = getDirection(currentPosition, newPosition);
        int distance = getDistance(currentPosition, newPosition);
        int steps = Math.abs(distance);
        int currentRow = currentPosition.getCol();
        int col = currentPosition.getCol();

        Position[] path = new Position[steps];
        for (int i = 0; i < steps; i++) {
            currentRow += direction;
            path[i] = new Position(col, currentRow);
        }
        return path;
    }

    public int getDirection(Position currentPosition, Position newPosition){
        return currentPosition.getRow() > newPosition.getRow() ? 1 : -1; // 1: right, -1: left;
    }

    public int getDistance(Position currentPosition, Position newPosition){
        return Math.abs(currentPosition.getRow() - newPosition.getRow());
    }

}

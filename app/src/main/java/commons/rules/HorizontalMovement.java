package commons.rules;

import commons.Position;

public class HorizontalMovement implements MovementRule{

    @Override
    public boolean validateMovement(Position currentPosition, Position newPosition) {
        int oldRow = currentPosition.getRow();
        int newRow = newPosition.getRow();
        return oldRow - newRow == 0;
    }

}
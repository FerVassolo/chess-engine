package chess.rules;

import commons.Position;

public class VerticalMovement implements MovementRule{

    @Override
    public boolean validateMovement(Position currentPosition, Position newPosition) {
        int oldCol = currentPosition.getCol();
        int newCol = newPosition.getCol();
        return oldCol - newCol == 0; //=0 --> true, !=0 false
    }
}

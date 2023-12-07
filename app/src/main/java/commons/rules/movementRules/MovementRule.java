package commons.rules.movementRules;

import commons.board.Position;

public interface MovementRule {

    /*Returns the pos on one iteration of the movement*/
    public boolean validateMovement(Position currentPosition, Position newPosition);

    // Though it is not
    public Position[] getPath(Position currentPosition, Position newPosition);
}
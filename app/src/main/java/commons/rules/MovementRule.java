package commons.rules;

import commons.Position;

public interface MovementRule {

    /*Returns the pos on one iteration of the movement*/
    public boolean validateMovement(Position currentPosition, Position newPosition);

}

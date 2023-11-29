package chess.rules;

import commons.Position;

public class LMovement implements MovementRule{

    @Override
    public boolean validateMovement(Position currentPosition, Position newPosition) {
        int[] subs = subs(currentPosition, newPosition);
        if(subs[0] == 1 && subs[1] == 2)
            return true;
        if(subs[0] ==2 && subs[1] == 1)
            return true;
        //System.out.println("Movement must be L-like"); // Cause Movement rules don't have messages.
        return false;
    }
    public int[] subs(Position currentPosition, Position newPosition){
        int[] currentPos = new int[]{currentPosition.getRow(), currentPosition.getCol()};
        int[] newPos = new int[]{newPosition.getRow(), newPosition.getCol()};
        int verticalSub = Math.abs(currentPos[0] - newPos[0]);
        int horizontalSub = Math.abs(currentPos[1] - newPos[1]);
        return new int[]{verticalSub, horizontalSub};
    }
}

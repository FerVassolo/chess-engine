package commons.result;

import commons.game.Player;

public class EndgameResult implements MoveResult{

    Player winner;
    public EndgameResult(Player winner){
        this.winner = winner;
    }

    public Player getWinner(){
        return this.winner;
    }
}

package commons.rules;

import commons.game.*;

import java.util.List;

public class RunOutOfPieces implements EndGameRule{
    @Override
    public boolean checkEndRule(Game game, Board board) {
        Player[] players = game.getPlayers();
        for(Player player : players){
            if(!colorHasRemainingPieces(board, player.getColor())){
                System.out.println(errorMessage(player.getColor()));
                return false;
            }
        }
        return true;
    }

    public boolean colorHasRemainingPieces(Board board, Color color){
        List<Piece> pieces = board.getAllPieces();
        for(Piece piece : pieces){
            if(piece.getColor() == color){
                return true;
            }
        }
        return false;
    }

    public String errorMessage(Color color){
        return color + " loses!";
    }
}

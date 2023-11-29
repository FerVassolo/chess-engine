package chess.game;

import commons.Board;
import commons.Game;
import commons.Movement;
import commons.Position;
import chess.rules.EndGameRule;

import java.util.ArrayList;

public class StartGame {
    // I should put this method inside another one that checks if the game ends
    public void startTurnBasedGame(Game game, Board board){
        //board.display();
        if(gameHasEnded(game, board))
            return;

        ArrayList<Position> positions = new ArrayList<>(game.askForMovement()); // In kotlin: fromPiece and toPiece
        Board newBoard = new Movement().makeMove(game, board, positions.get(0), positions.get(1));
        if (board.equals(newBoard)){
            //System.out.println("Movement is not valid, try again");
            startTurnBasedGame(game, board);
        }
        else{
            game.passTurn();
            game.setHistoryOfBoards(newBoard);
            startTurnBasedGame(game, newBoard);
        }
    }

    public boolean gameHasEnded(Game game, Board board){
        for(EndGameRule rule : game.getEndGameRules()){
            if(!rule.checkEndRule(game, board)){
                return true;
            }
        }
        return false;
    }
}

package commons.game;

import commons.board.Board;
import commons.board.Position;
import commons.movement.Movement;
import commons.movement.MovementValidator;
import commons.result.EndgameResult;
import commons.result.InvalidMoveResult;
import commons.result.MoveResult;
import commons.result.ValidMoveResult;
import commons.rules.endgameRules.EndGameRule;
import commons.rules.restrictionRules.RestrictionRule;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private RestrictionRule[] gameRules;
    private Player[] players;
    private EndGameRule[] endGameRules;
    private Board board;
    private Time gameClock;
    private int turn = 0;

    // All the boards used in the game. Useful for checking the on passant, the pawns first double movement or the king castle.
    //It ain't actually inmutable.
    private ArrayList<Board> historyOfBoards = new ArrayList<>();

    public Game(RestrictionRule[] gameRules, EndGameRule[] endGameRules, Player[] players, Board board, Time gameClock){
        this.gameRules = gameRules;
        this.players = players;
        this.board = board;
        this.gameClock = gameClock;
        this.endGameRules = endGameRules;
        historyOfBoards.add(board);
    }

    public Game(Game game){
        this.gameRules = game.getGameRules();
        this.players = game.getPlayers();
        this.board = game.getBoard();
        this.gameClock = getGameClock();
        this.endGameRules = game.getEndGameRules();
        this.turn = game.getTurn();
    }

    public MoveResult move( Board board, Position originalPos, Position newPos){

        if(gameHasEnded(this, board)){
            passTurn();
            return new EndgameResult(currentTurn());
        }

        if(board.getPiece(originalPos) == null)
            return new InvalidMoveResult("No piece in " + originalPos.getRow() + ", " + originalPos.getRow());
        if(board.getPiece(originalPos).getColor() != currentTurn().getColor()){
            return new InvalidMoveResult("Piece does not belong to the current player");
        }
        if(board.getPiece(newPos) != null && board.getPiece(newPos).getColor() == currentTurn().getColor()){
            return new InvalidMoveResult("There is a piece in " +  newPos.getRow() + ", " + newPos.getRow());
        }

        Board newBoard = new Movement().makeMove(this, board, originalPos, newPos);
        if (board.equals(newBoard)){
            return new InvalidMoveResult(invalidationMessage(this, getLastBoard(), originalPos, newPos));
        }
        else{
            this.passTurn();
            this.setHistoryOfBoards(newBoard);
            return new ValidMoveResult(this);
        }
    }

    public String invalidationMessage(Game game, Board board, Position from, Position to) {
        MovementValidator validator = new MovementValidator();
        validator.validateMovement(game, board, from, to);
        return validator.getError();
    }

    public boolean gameHasEnded(Game game, Board board){
        for(EndGameRule rule : game.getEndGameRules()){
            if(!rule.checkEndRule(game, board)){
                return true;
            }
        }
        return false;
    }

    public EndGameRule[] getEndGameRules() {
        return endGameRules;
    }
    public Time getGameClock() {
        return gameClock;
    }
    public void passTurn(){
        if(turn + 1>= players.length)
            turn = 0;
        else ++turn;
    }
    public Player currentTurn(){
        return players[turn];
    }
    public Board getBoard() {
        return board;
    }
    public Player[] getPlayers() {
        return players;
    }
    public RestrictionRule[] getGameRules() {
        return gameRules;
    }
    public void setHistoryOfBoards(Board newBoard){
        historyOfBoards.add(newBoard);
    }
    public Board getLastBoard(){return historyOfBoards.get(historyOfBoards.size()-1);}
    public Player getCurrentPlayer(){
        return players[turn];
    }
    public ArrayList<Board> getHistoryOfBoards() {
        return historyOfBoards;
    }
    public int getTurn(){
        return turn;
    }


}

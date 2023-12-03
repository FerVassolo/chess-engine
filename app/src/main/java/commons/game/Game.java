package commons.game;

import commons.rules.EndGameRule;
import commons.rules.RestrictionRule;

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

    public ArrayList<Position> askForMovement(){
        System.out.println();
        System.out.println("It is " + players[turn].getName() + "'s turn");
        System.out.println("Select a piece to move (row, col): ");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Row: ");
        int currentRow = scanner.nextInt();
        System.out.print("Column: ");
        int currentCol = scanner.nextInt();

        System.out.println("New position (row, col): ");
        System.out.print("Row: ");
        int newRow = scanner.nextInt();
        System.out.print("Column: ");
        int newCol = scanner.nextInt();
        System.out.println();

        Position currentPos = new Position(currentRow, currentCol);
        Position newPos = new Position(newRow, newCol);

        ArrayList<Position> arrayList = new ArrayList<>();
        arrayList.add(currentPos);
        arrayList.add(newPos);

        return arrayList;
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

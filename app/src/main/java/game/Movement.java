package game;

import rules.MovementRule;
import rules.RestrictionRule;
import rules.SpecialRule;
import rules.StraightMaxQuantityRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Movement {
    /*Returns a new board or the original board depending on weather it is a valid movement or not*/

    public Board makeMove(Game game, Board board, Position oldPos, Position newPos) {
        boolean isValid = validateMovement(game, board, oldPos, newPos);

        if (isValid) {
           return movePiece(board, oldPos, newPos);
        }
        return board;
    }

    public Board movePiece(Board board, Position oldPos, Position newPos){
        Map<Position, Piece> newPosDisplay = new HashMap<>(board.getPositions()); // Create a copy
        Piece movedPiece = newPosDisplay.get(board.getPosByPos(oldPos));
        newPosDisplay.put(board.getPosByPos(oldPos), null);
        newPosDisplay.put(board.getPosByPos(newPos), movedPiece);
        return new Board(newPosDisplay, board.getHeight(), board.getWidth());
    }

    public boolean validateMovement(Game game, Board board, Position currentPos, Position newPos){
        Piece piece = board.getPiece(currentPos);
        if(piece == null){
            System.out.println("There is no piece on that position");
            return false;
        }
        RestrictionRule[] restrictionRules = appendRestrictionRules(game.getGameRules(), piece.getRestrictionRules());
        MovementRule[] movementRules = piece.getMovementRules();
        SpecialRule[] specialRules = piece.getSpecialRules();

        if(!selectedPieceColorIsValid(piece, game.currentTurn()))
            return false;

        if(specialRules.length > 0){
            for(SpecialRule sp : specialRules){
                if(sp.specialRuleIsActive(currentPos, game.getHistoryOfBoards())){
                    RestrictionRule[] resRules = appendRestrictionRules(game.getGameRules(), sp.getRestrictionRules());
                    boolean isValid = isValid(movementRules, resRules, currentPos, newPos, board);
                    if(isValid) return true;
                }
            }
        }
        for(RestrictionRule rule : restrictionRules){
            if (rule instanceof StraightMaxQuantityRule) {
                System.out.println(((StraightMaxQuantityRule) rule).maxQty);
            }
        }
        return isValid(movementRules, restrictionRules, currentPos, newPos, board);
    }
    public boolean isValid(MovementRule[] movementRules, RestrictionRule[] restrictionRules, Position currentPos, Position newPos, Board board){
        for (RestrictionRule resRule: restrictionRules){
            if(!resRule.validateRule(currentPos, newPos, board))
                return false;
        }

        for(MovementRule rule: movementRules){

            if(!rule.validateMovement(currentPos, newPos))
                continue;
            return true;
        }
        return false;
    }

    public RestrictionRule[] appendRestrictionRules(RestrictionRule[] gameRules, RestrictionRule[] restrictionRules){
        RestrictionRule[] combinedRules = new RestrictionRule[gameRules.length + restrictionRules.length];
        System.arraycopy(gameRules, 0, combinedRules, 0, gameRules.length);
        System.arraycopy(restrictionRules, 0, combinedRules, gameRules.length, restrictionRules.length);
        return combinedRules;
    }

    public boolean selectedPieceColorIsValid(Piece piece, Player player){
        if(piece.getColor() != player.getColor()) {
            System.out.println("Invalid Color");
            return false;
        }
        return true;
    }

    public ArrayList<Position> getPieceAllPossibleMoves(Position piecePos, Board board, Game game){
        ArrayList<Position> array = new ArrayList<>();
        Game newGame = new Game(game);
        // If we want to check the position of a piece of the color that isn't moving
        if(game.getCurrentPlayer().getColor() != board.getPiece(piecePos).getColor())
            newGame.passTurn();
        for(Position pos : board.getPositionsMapKeys()){
            if(validateMovement(newGame, board, piecePos, pos))
                array.add(pos);
        }
        // could happen that array is null
        return array;
    }

}

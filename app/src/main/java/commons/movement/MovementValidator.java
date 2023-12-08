package commons.movement;

import commons.board.Board;
import commons.game.Game;
import commons.game.Player;
import commons.board.Position;
import commons.piece.Piece;
import commons.rules.boardDependantRules.BoardDependantSpecialRule;
import commons.rules.boardHistoryDependantRules.BoardHistoryDependantSpecialRule;
import commons.rules.movementRules.MovementRule;
import commons.rules.restrictionRules.RestrictionRule;
import commons.rules.commonSpecialRules.SpecialRule;

public class MovementValidator {

    private String error;

    public boolean validateMovement(Game game, Board board, Position currentPos, Position newPos) {
        Piece piece = board.getPiece(currentPos);

        if (piece == null) return false;

        RestrictionRule[] restrictionRules = appendRestrictionRules(game.getGameRules(), piece.getRestrictionRules());
        MovementRule[] movementRules = piece.getMovementRules();
        SpecialRule[] specialRules = piece.getSpecialRules();
        BoardHistoryDependantSpecialRule[] histDependantSpecialRules = piece.getDependantSpecialRules();

        if (!selectedPieceColorIsValid(piece, game.currentTurn())) return false;

        if(validateHistoryDependantSpecialRules(currentPos, newPos, histDependantSpecialRules, game)) return true;

        if(validateSpecialRules(currentPos, newPos, specialRules, movementRules, game, board)) return true;

        return isValid(movementRules, restrictionRules, currentPos, newPos, board);
    }

    public boolean validateSpecialRules(Position currentPos, Position newPos, SpecialRule[] specialRules, MovementRule[] movementRules, Game game, Board board){
        for (SpecialRule sp : specialRules) {
            if (sp.specialRuleIsActive(currentPos, game.getHistoryOfBoards())) {
                RestrictionRule[] resRules = appendRestrictionRules(game.getGameRules(), sp.getRestrictionRules());
                boolean isValid = isValid(movementRules, resRules, currentPos, newPos, board);
                if (isValid) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validateHistoryDependantSpecialRules(Position currentPos, Position newPos, BoardHistoryDependantSpecialRule[] specialRules, Game game){
        for (BoardHistoryDependantSpecialRule sp : specialRules) {
            if (sp.ruleIsActive(currentPos, newPos, game.getHistoryOfBoards())) {
                return true;
            }
        }
        return false;
    }

    public  boolean isValid(MovementRule[] movementRules, RestrictionRule[] restrictionRules, Position currentPos, Position newPos, Board board) {
        for (RestrictionRule resRule : restrictionRules) {
            if (!resRule.validateRule(currentPos, newPos, board)) { // if false
                error = resRule.errorMessage();
                return false;
            }
        }

        for (MovementRule rule : movementRules) {
            if (rule.validateMovement(currentPos, newPos)) {
                return true;
            }
        }
        error = "Piece cannot move on that direction.";
        return false;
    }

    private static RestrictionRule[] appendRestrictionRules(RestrictionRule[] gameRules, RestrictionRule[] restrictionRules) {
        RestrictionRule[] combinedRules = new RestrictionRule[gameRules.length + restrictionRules.length];
        System.arraycopy(gameRules, 0, combinedRules, 0, gameRules.length);
        System.arraycopy(restrictionRules, 0, combinedRules, gameRules.length, restrictionRules.length);
        return combinedRules;
    }

    private static boolean selectedPieceColorIsValid(Piece piece, Player player) {
        if (piece.getColor() != player.getColor()) {
            return false;
        }
        return true;
    }

    public boolean boardDependantIsValid(Board board, Position oldPos, Position newPos) {
        Piece movedPiece = board.getPiece(oldPos);

        for (BoardDependantSpecialRule rule : movedPiece.getBoardDependantRules()) {
            if (rule.ruleIsActive(oldPos, newPos, board)) {
                return true;
            }
        }
        return false;
    }

    public String getError() {
        return this.error;
    }
}

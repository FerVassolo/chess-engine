package commons.game;

import commons.rules.BoardHistoryDependantSpecialRule;
import commons.rules.MovementRule;
import commons.rules.RestrictionRule;
import commons.rules.SpecialRule;

public class MovementValidator {

    public boolean validateMovement(Game game, Board board, Position currentPos, Position newPos) {
        Piece piece = board.getPiece(currentPos);

        if (piece == null) {
            System.out.println("There is no piece on that position");
            return false;
        }

        RestrictionRule[] restrictionRules = appendRestrictionRules(game.getGameRules(), piece.getRestrictionRules());
        MovementRule[] movementRules = piece.getMovementRules();
        SpecialRule[] specialRules = piece.getSpecialRules();
        BoardHistoryDependantSpecialRule[] dependantSpecialRules = piece.getDependantSpecialRules();

        if (!selectedPieceColorIsValid(piece, game.currentTurn()))
            return false;

        if(validateHistoryDependantSpecialRules(currentPos, newPos, dependantSpecialRules, game))
            return true;

        if(validateSpecialRules(currentPos, newPos, specialRules, movementRules, game, board))
            return true;

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

    public static boolean isValid(MovementRule[] movementRules, RestrictionRule[] restrictionRules, Position currentPos, Position newPos, Board board) {
        for (RestrictionRule resRule : restrictionRules) {
            if (!resRule.validateRule(currentPos, newPos, board)) { // if false
                return false;
            }
        }

        for (MovementRule rule : movementRules) {
            if (rule.validateMovement(currentPos, newPos)) {
                return true;
            }
        }
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
            System.out.println("Invalid Color");
            return false;
        }
        return true;
    }
}

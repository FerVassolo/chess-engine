package rules;

import game.*;

import java.util.ArrayList;

public class CheckMate implements EndGameRule{

    /**
    THE CHECKMATE VERIFICATION PROCESS:<br/>
    With any of these points returning true there is no checkmate. We verify them in order.<br/>
    Worst case scenario: we reach point 5.<br/>
       1. Check if current pos is in check.<br/>
       2. Evaluate king escape.<br/>
       3. Get all the attacking pieces<br/>
       4. Block or capture the attacking piece. (Only if there is just ONE attacking piece)<br/>
&nbsp;&nbsp;&nbsp;a) For each of the defense piece, check if they can reach the attacking peace<br/>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;i) They cannot reach if they leave the board in check, so no trouble in implemented that. It is already implemented<br/>
       5. Interpose a piece.<br/>
     &nbsp;&nbsp;&nbsp;a) Get all the attacking peace possible moves.<br/>
     &nbsp;&nbsp;&nbsp;b) Try to block them.<br/>
              --> Just by finding a movement that prevents checkmate its enough. So usually we don't reach the worst case.<br/>
          * */

    Check check = new Check();

    @Override
    public boolean checkEndRule(Game game, Board board) {
        check.suppressStandardOutput();
        Color currentColor = game.getCurrentPlayer().getColor();
        Position kingPos = check.getKingPos(board, currentColor);
        if(isNotInCheck(board, currentColor)){
            check.restoreStandardOutput();
            //System.out.println("There is not Check");
            return true;
        }
        //System.out.println("Check");

        if(kingCanEscape(board, currentColor, game)){
            check.restoreStandardOutput();
            //System.out.println("King can escape");
            return true;
        }
        //System.out.println("King cannot escape");
        ArrayList<Position> attackingPieces = getAllAttackingPeaces(board, currentColor);
            // If I can defend 1 then its enough cause that means I can defend all.
            // If I cannot defend one then its checkmate eve though I can defend all else.
        if(canDefendPieceFromPiece(kingPos, attackingPieces.get(0), board, game)) {
            check.restoreStandardOutput();
            //System.out.println("A piece can defend the king");
            return true;
        }
        else{
            game.passTurn();
            check.restoreStandardOutput();
            System.out.println("CheckMate: " + game.getCurrentPlayer().getColor() + " wins!");
            return false;
        }

    }

    public boolean isNotInCheck(Board board, Color color){
        return check.verifyBoardIsNotInCheck(board, color);
    }

    private ArrayList<Position> getAllAttackingPeaces(Board board, Color color){
        Position kingPos = check.getKingPos(board, color);
        ArrayList<Position> positions = new ArrayList<>();
        appendAttackingPieces(kingPos, color, positions, board);
        return positions;
    }

    private void appendAttackingPieces(Position attackedPos, Color color, ArrayList<Position> positions, Board board){
        for(Position pos : board.getPositionsMapKeys()) {
            if(check.pieceIsMenacingPos(pos, attackedPos, board, color)) {
                positions.add(pos);
            }
        }
    }

    public boolean kingCanEscape(Board board, Color color, Game game){
        Position kingPos = check.getKingPos(board, color);
        return pieceCanMove(board, kingPos, game);
    }


    // Brute Force, an optimization is:
    // I get all the positions, if they are not reachable by any Movement Rule I don't check the restrictions.
    public boolean pieceCanMove(Board board, Position piecePos, Game game){
        for(Position pos : board.getPositionsMapKeys()){
            if(isReachable(board, game, piecePos, pos))
                return true;
        }
        return false;
    }

    public boolean isReachable(Board board, Game game,  Position currentPos, Position newPos){
        return new MovementValidator().validateMovement(game, board, currentPos, newPos);
    }

    public boolean canDefendPieceFromPiece(Position defendedPos, Position attackingPos, Board board, Game game){
        Color defendingColor = board.getPiece(defendedPos).getColor();
        for(Position pos : board.getPositionsMapKeys()){
            Piece piece = board.getPiece(pos);
            if(check.pieceIsOfColor(piece, defendingColor)){
                if(pieceCanCapturePiece(pos, attackingPos, board, game)) return true;
                if(colorCanBlockPiece(pos, attackingPos, board, game)) return true;
            }
        }
        return false;
    }

    // This one is not working
    public boolean pieceCanCapturePiece(Position defendingPiece, Position attackingPiece, Board board, Game game){
        return new MovementValidator().validateMovement(game, board, defendingPiece, attackingPiece);
    }

    public boolean colorCanBlockPiece(Position defendingPos, Position attackingPiece, Board board, Game game){
        ArrayList<Position> possibleBlockingPositions = new Movement().getPieceAllPossibleMoves(attackingPiece, board, game);
        ArrayList<Position> allDefendingColorPieces = board.getAllPositionsOfColor(board.getPiece(defendingPos).getColor());
        for(Position pos : allDefendingColorPieces){
            if(pieceCanBlockPiece(pos, board, game, possibleBlockingPositions)) return true;
        }
        return false;
    }


    public boolean pieceCanBlockPiece(Position defendingPos, Board board, Game game, ArrayList<Position> possibleBlockingPositions) {

        for(Position pos : possibleBlockingPositions){
            if(new MovementValidator().validateMovement(game, board, defendingPos, pos)){
                return true;
            }
        }
        return false;
    }
}

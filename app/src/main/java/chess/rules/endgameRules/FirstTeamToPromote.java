package chess.rules.endgameRules;

import commons.board.Board;
import commons.game.Game;
import commons.piece.Piece;
import commons.piece.PieceName;
import commons.rules.endgameRules.EndGameRule;
import commons.rules.endgameRules.RunOutOfPieces;

import java.util.List;
import java.util.stream.Collectors;

public class FirstTeamToPromote implements EndGameRule {
    
    // Wins the first to promote or whoever runs out of pieces
    @Override
    public boolean checkEndRule(Game game, Board board) {
        System.out.println("Rule called");
        List<Piece> pieces = board.getAllPieces();
        EndGameRule runOut = new RunOutOfPieces();
        if(!runOut.checkEndRule(game, board))
            return false;

        List<Piece> queenPieces =  getOnlyQueens(pieces);
        Board firstBoard = game.getHistoryOfBoards().get(0);
        System.out.println(queenPieces.size());
        for(Piece queen : queenPieces){
            System.out.println(queen.getName());
            Piece originalPiece = firstBoard.getPieceById(queen.getId());
            System.out.println(originalPiece.getName());
            if(originalPiece.getName() != PieceName.QUEEN){
                return false;
            }
        }
        return true;

    }

    public List<Piece> getOnlyQueens(List<Piece> pieces){
        return pieces.stream()
                .filter(piece -> piece.getName() == PieceName.QUEEN)
                .collect(Collectors.toList());
    }

}

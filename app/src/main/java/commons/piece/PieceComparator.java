package commons.piece;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class PieceComparator implements Comparator<PieceName> {

    private static final Map<PieceName, Integer> pieceHierarchy = new HashMap<>();

    static {
        pieceHierarchy.put(PieceName.CHECKERS_KING, 2); // maybe should be avobe null, dunno
        pieceHierarchy.put(PieceName.QUEEN, 2);
        pieceHierarchy.put(PieceName.KING, 2);
        pieceHierarchy.put(PieceName.ROOK, 2);
        pieceHierarchy.put(PieceName.KNIGHT, 2);
        pieceHierarchy.put(PieceName.BISHOP, 2);
        pieceHierarchy.put(PieceName.PAWN, 1);
        pieceHierarchy.put(PieceName.MAN, 1);

    }

    @Override
    public int compare(PieceName piece1, PieceName piece2) {
        Integer hierarchyValue1 = pieceHierarchy.getOrDefault(piece1, 0);
        Integer hierarchyValue2 = pieceHierarchy.getOrDefault(piece2, 0);
        return Integer.compare(hierarchyValue1, hierarchyValue2);
    }

}

package game;

public interface PieceFactory {
    public Piece createPiece(int id, Color color);
    public Piece createPieceWithSpecialRules(int id, Color color);
}

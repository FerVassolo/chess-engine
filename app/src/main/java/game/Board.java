package game;

import java.util.*;

public class Board {

    private Map<Position, Piece> positions;
    private int height;
    private int width;
    private String displayBoard;
    /*Creating an empty Board*/

    public Board(int height, int width){
        this.height = height;
        this.width = width;
        this.positions = createEmptyBoard();
    }

    /*Updating the board*/
    public Board(Map<Position, Piece> positions, int height, int width){
        this.positions = positions;
        this.height = height;
        this.width = width;
    }

    public Map<Position, Piece> createEmptyBoard(){
        Map<Position, Piece> positions = new HashMap<>();
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                Position position = new Position(i, j);
                positions.put(position, null);
                //positions.put(position, new Piece(i+j, PieceName.PAWN, "P", Color.BLACK, new MovementRule[]{new StraightMovement(1)}, new RestrictionRule[]{new StraightMaxQuantityRule(1)}));
            }
        }
        return positions;
    }

    /*At the beggining all displays will be squares*/
    public void display(){
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                if(positions.get(getPosByAxis(i, j)) != null){
                    String pieceName = positions.get(getPosByAxis(i, j)).getNameAbbreviation();
                    if(positions.get(getPosByAxis(i, j)).getNameAbbreviation().length() == 3){
                        System.out.print("| " + pieceName + "");
                    }
                    else
                        System.out.print("| " + pieceName + " ");
                }
                else
                    System.out.print("|    ");
                if(j == height-1)
                    System.out.print("|");
            }
            System.out.println();
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Map<Position, Piece> getPositions() {
        return positions;
    }

    public Set<Position> getPositionsMapKeys(){
        return positions.keySet();
    }
    public Position getPosByPos(Position pos){
        return getPosByAxis(pos.getRow(), pos.getCol());
    }

    public Position getPosByAxis(int row, int col){
        for(Position pos : positions.keySet()){
            if (pos.getRow() == row && pos.getCol() == col){
                return pos;
            }
        }
        return null;
    }

    // Put any position, it doesn't matter if it doesn't exist it will change it into an existing one.
    public Piece getPiece(Position position){
        Position pos = getPosByAxis(position.getRow(), position.getCol());
        return positions.get(pos);
    }
    public Piece getPieceByVector(int row, int col){
        Position pos = getPosByAxis(row, col);
        return getPiece(pos);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (height != board.height) return false;
        if (width != board.width) return false;
        if (!positions.equals(board.getPositions())){
            return false;
        }

        return true;
    }


    @Override
    public int hashCode() {
        return Objects.hash(positions, height, width);
    }


    public ArrayList<Position> getAllPositionsOfColor(Color color){
        ArrayList<Position> array = new ArrayList<>();
        for(Position pos : getPositionsMapKeys()){
            if(pieceIsOfColor(getPiece(pos), color)) {
                array.add(pos);
            }
        }
        return array;
    }


    public boolean pieceIsOfColor(Piece piece, Color color){
        if(piece == null) return false;
        return piece.getColor() == color;
    }
}

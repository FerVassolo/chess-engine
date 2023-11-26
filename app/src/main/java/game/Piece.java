package game;

import rules.MovementRule;
import rules.PromotionRule;
import rules.RestrictionRule;
import rules.SpecialRule;


public class Piece {
    private int id;
    private PieceName name;
    private String nameAbbreviation; // used for displaying
    private Color color;
    private MovementRule[] movementRules;
    private RestrictionRule[] restrictionRules;
    private SpecialRule[] specialRules;
    private PromotionRule[] promotionRules;


    public Piece(int id, PieceName name, String nameAbbreviation, Color color, MovementRule[] movementRules, RestrictionRule[] restrictionRules){
        this.id = id;
        this.nameAbbreviation = abbreviation(nameAbbreviation, color);
        this.color = color;
        this.name = name;
        this.movementRules = movementRules;
        this.restrictionRules = restrictionRules;
        this.specialRules = new SpecialRule[]{};
        this.promotionRules = new PromotionRule[]{};
    }

    public Piece(int id, PieceName name, String nameAbbreviation, Color color, MovementRule[] movementRules, RestrictionRule[] restrictionRules, SpecialRule[] specialRules){
        this.id = id;
        this.nameAbbreviation = abbreviation(nameAbbreviation, color);
        this.color = color;
        this.name = name;
        this.movementRules = movementRules;
        this.restrictionRules = restrictionRules;
        this.specialRules = specialRules;
        this.promotionRules = new PromotionRule[]{};
    }
    public Piece(int id, PieceName name, String nameAbbreviation, Color color, MovementRule[] movementRules, RestrictionRule[] restrictionRules, SpecialRule[] specialRules, PromotionRule[] promotionRules){
        this.id = id;
        this.nameAbbreviation = abbreviation(nameAbbreviation, color);
        this.color = color;
        this.name = name;
        this.movementRules = movementRules;
        this.restrictionRules = restrictionRules;
        this.specialRules = specialRules;
        this.promotionRules = promotionRules;
    }
    public Color getColor() {
        return color;
    }

    public MovementRule[] getMovementRules() {
        return movementRules;
    }

    public RestrictionRule[] getRestrictionRules() {
        return restrictionRules;
    }

    public String getNameAbbreviation() {
        return nameAbbreviation;
    }
    public SpecialRule[] getSpecialRules(){
        return specialRules;
    }

    public PieceName getName() {
        return name;
    }
    private String abbreviation(String nameAbbreviation, Color color){
        return getColorFirstLet(color) + nameAbbreviation;
    }
    public char getColorFirstLet(Color color){
        return color.toString().toLowerCase().charAt(0);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // If the objects are the same instance, they are equal
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false; // If the object is null or of a different class, they are not equal
        }

        Piece otherPiece = (Piece) obj; // Cast the object to a Piece

        // Compare all attributes for equality
        return id == otherPiece.id &&
                name == otherPiece.name &&
                nameAbbreviation.equals(otherPiece.nameAbbreviation) &&
                color == otherPiece.color;
    }

    public PromotionRule[] getPromotionRules() {
        return promotionRules;
    }

    public int getId() {
        return id;
    }
    /*Hago la class movement, agarro la primer movement rule y meto una iteración
    * Agarro las restrictions y gameRules y veo si puedo seguir y si es valido ese move*/
}

package game;

import java.sql.Time;

public class Player {

    private String name;
    private int id;
    private Time remainingTime; //(?) todo
    private Color color;

    public Player(String name, int id, Time remainingTime, Color color){
        this.name = name;
        this.id = id;
        this.remainingTime = remainingTime;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}

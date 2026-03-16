package org.example;

public class Chess {
    private final String name;
    private final int weight;
    private final Side side;
    private int loc;
    private boolean revealed;
    private boolean alive;

    public Chess(String name, int weight, Side side, int loc) {
        this.name = name;
        this.weight = weight;
        this.side = side;
        this.loc = loc;
        this.revealed = false;
        this.alive = true;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public Side getSide() {
        return side;
    }

    public int getLoc() {
        return loc;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public String toString() {
        return "Chess{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", side=" + side +
                ", loc=" + loc +
                ", revealed=" + revealed +
                ", alive=" + alive +
                '}';
    }
}
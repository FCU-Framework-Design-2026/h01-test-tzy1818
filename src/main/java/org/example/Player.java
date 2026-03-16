package org.example;

public class Player {
    private final String name;
    private final Side side;

    public Player(String name, Side side) {
        this.name = name;
        this.side = side;
    }

    public String getName() {
        return name;
    }

    public Side getSide() {
        return side;
    }

    @Override
    public String toString() {
        return name + "(" + side + ")";
    }
}
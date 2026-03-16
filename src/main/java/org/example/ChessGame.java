package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChessGame extends AbstractGame {
    private final Chess[] board = new Chess[32];

    private Player p1;
    private Player p2;
    private Player currentPlayer;

    public void generateChess() {
        List<Chess> pieces = new ArrayList<>();

        addSidePieces(pieces, Side.RED);
        addSidePieces(pieces, Side.BLACK);

        Collections.shuffle(pieces);

        for (int i = 0; i < 32; i++) {
            Chess c = pieces.get(i);
            c.setLoc(i);
            board[i] = c;
        }
    }

    private void addSidePieces(List<Chess> pieces, Side side) {
        pieces.add(new Chess("將", 7, side, -1));
        pieces.add(new Chess("士", 6, side, -1));
        pieces.add(new Chess("士", 6, side, -1));
        pieces.add(new Chess("象", 5, side, -1));
        pieces.add(new Chess("象", 5, side, -1));
        pieces.add(new Chess("車", 4, side, -1));
        pieces.add(new Chess("車", 4, side, -1));
        pieces.add(new Chess("馬", 3, side, -1));
        pieces.add(new Chess("馬", 3, side, -1));
        pieces.add(new Chess("包", 2, side, -1));
        pieces.add(new Chess("包", 2, side, -1));

        for (int i = 0; i < 5; i++) {
            pieces.add(new Chess("兵", 1, side, -1));
        }
    }

    public void showAllChess() {
        System.out.println("   1  2  3  4  5  6  7  8");

        for (int row = 0; row < 4; row++) {
            char rowName = (char) ('A' + row);
            System.out.print(rowName + "  ");

            for (int col = 0; col < 8; col++) {
                int index = row * 8 + col;
                Chess chess = board[index];

                if (chess == null) {
                    System.out.print("＿ ");
                } else if (!chess.isRevealed()) {
                    System.out.print("Ｘ ");
                } else {
                    System.out.print(chess.getName() + " ");
                }

                if (col != 7) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void showAllChessDetails() {
        System.out.println("\n=== 棋子詳細資訊 ===");
        for (Chess chess : board) {
            if (chess != null) {
                System.out.println(chess.getName() + " | " +
                        chess.getSide() + " | " +
                        PositionUtil.toBoardPosition(chess.getLoc()));
            }
        }
    }

    @Override
    public void setPlayers(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.currentPlayer = p1;
    }

    @Override
    public boolean gameOver() {
        boolean redAlive = false;
        boolean blackAlive = false;

        for (Chess chess : board) {
            if (chess != null && chess.isAlive()) {
                if (chess.getSide() == Side.RED) {
                    redAlive = true;
                } else {
                    blackAlive = true;
                }
            }
        }

        return !(redAlive && blackAlive);
    }

    @Override
    public boolean move(int location) {
        if (!isValidIndex(location)) {
            return false;
        }

        Chess chess = board[location];

        if (chess == null || !chess.isAlive()) {
            return false;
        }

        if (!chess.isRevealed()) {
            chess.setRevealed(true);
            System.out.println("翻開成功：" + chess.getName() + " (" + chess.getSide() + ")");
            switchTurn();
            return true;
        }

        return false;
    }

    public boolean move(int from, int to) {
        if (!isValidIndex(from) || !isValidIndex(to)) {
            return false;
        }

        if (from == to) {
            return false;
        }

        Chess source = board[from];
        Chess target = board[to];

        if (source == null || !source.isAlive()) {
            return false;
        }

        if (!source.isRevealed()) {
            return false;
        }

        if (source.getSide() != currentPlayer.getSide()) {
            return false;
        }

        if (!isAdjacent(from, to)) {
            return false;
        }

        if (target == null) {
            board[to] = source;
            board[from] = null;
            source.setLoc(to);

            System.out.println("移動成功：" +
                    PositionUtil.toBoardPosition(from) + " -> " +
                    PositionUtil.toBoardPosition(to));

            switchTurn();
            return true;
        }

        if (!target.isAlive()) {
            return false;
        }

        if (!target.isRevealed()) {
            return false;
        }

        if (target.getSide() == source.getSide()) {
            return false;
        }

        if (!canCapture(source, target)) {
            return false;
        }

        target.setAlive(false);
        board[to] = source;
        board[from] = null;
        source.setLoc(to);

        System.out.println("吃子成功：" +
                source.getName() + " 吃掉 " + target.getName());

        switchTurn();
        return true;
    }

    private boolean canCapture(Chess attacker, Chess defender) {
        return attacker.getWeight() >= defender.getWeight();
    }

    private boolean isAdjacent(int a, int b) {
        int rowA = a / 8;
        int colA = a % 8;

        int rowB = b / 8;
        int colB = b % 8;

        return Math.abs(rowA - rowB) + Math.abs(colA - colB) == 1;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < 32;
    }

    private void switchTurn() {
        currentPlayer = (currentPlayer == p1) ? p2 : p1;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Chess getChessAt(int index) {
        if (!isValidIndex(index)) {
            return null;
        }
        return board[index];
    }

    public Player getWinner() {
        boolean redAlive = false;
        boolean blackAlive = false;

        for (Chess chess : board) {
            if (chess != null && chess.isAlive()) {
                if (chess.getSide() == Side.RED) {
                    redAlive = true;
                } else {
                    blackAlive = true;
                }
            }
        }

        if (redAlive && !blackAlive) {
            return p1.getSide() == Side.RED ? p1 : p2;
        }

        if (blackAlive && !redAlive) {
            return p1.getSide() == Side.BLACK ? p1 : p2;
        }

        return null;
    }
}
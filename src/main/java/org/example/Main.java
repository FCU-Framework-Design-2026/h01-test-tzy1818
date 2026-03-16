package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        game.generateChess();

        Player player1 = new Player("玩家1", Side.RED);
        Player player2 = new Player("玩家2", Side.BLACK);
        game.setPlayers(player1, player2);

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 象棋翻棋遊戲（Console 版）===");
        System.out.println("輸入範例：A2、B3、C7");
        System.out.println("規則簡化：相鄰移動，相鄰吃子，數值大吃小。");
        System.out.println();

        game.showAllChessDetails();

        while (!game.gameOver()) {
            System.out.println();
            game.showAllChess();
            System.out.println();
            System.out.println("目前回合：" + game.getCurrentPlayer().getName() +
                    " / " + game.getCurrentPlayer().getSide());

            System.out.print("請輸入要選擇的棋子位置：");
            String input = scanner.nextLine();

            int from = PositionUtil.parsePosition(input);
            if (from == -1) {
                System.out.println("輸入格式錯誤，請重新輸入，例如 A2");
                continue;
            }

            Chess selected = game.getChessAt(from);
            if (selected == null) {
                System.out.println("該位置沒有棋子。");
                continue;
            }

            if (!selected.isRevealed()) {
                boolean flipped = game.move(from);
                if (!flipped) {
                    System.out.println("翻棋失敗。");
                }
                continue;
            }

            if (selected.getSide() != game.getCurrentPlayer().getSide()) {
                System.out.println("這不是你這一回合可以操作的棋子。");
                continue;
            }

            System.out.print("該棋已翻開，請輸入目的位置：");
            String targetInput = scanner.nextLine();

            int to = PositionUtil.parsePosition(targetInput);
            if (to == -1) {
                System.out.println("目的位置格式錯誤，請重新輸入，例如 B4");
                continue;
            }

            boolean moved = game.move(from, to);
            if (!moved) {
                System.out.println("移動或吃子失敗，可能原因如下：");
                System.out.println("1. 不是相鄰位置");
                System.out.println("2. 目的地是己方棋子");
                System.out.println("3. 目的地棋子尚未翻開");
                System.out.println("4. 吃子規則不符");
            }
        }

        System.out.println();
        game.showAllChess();
        System.out.println("=== 遊戲結束 ===");

        Player winner = game.getWinner();
        if (winner != null) {
            System.out.println("勝利者：" + winner.getName() + " / " + winner.getSide());
        } else {
            System.out.println("平手或未判定。");
        }

        scanner.close();
    }
}
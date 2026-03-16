package org.example;

public class PositionUtil {

    private PositionUtil() {
    }

    public static int parsePosition(String input) {
        if (input == null) {
            return -1;
        }

        input = input.trim().toUpperCase();

        if (input.length() != 2) {
            return -1;
        }

        char rowChar = input.charAt(0);
        char colChar = input.charAt(1);

        if (rowChar < 'A' || rowChar > 'D') {
            return -1;
        }

        if (colChar < '1' || colChar > '8') {
            return -1;
        }

        int row = rowChar - 'A';
        int col = colChar - '1';

        return row * 8 + col;
    }

    public static String toBoardPosition(int index) {
        if (index < 0 || index >= 32) {
            return "INVALID";
        }

        int row = index / 8;
        int col = index % 8;

        char rowChar = (char) ('A' + row);
        char colChar = (char) ('1' + col);

        return "" + rowChar + colChar;
    }
}
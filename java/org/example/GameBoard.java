package org.example;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard implements Serializable {
    private int[][] board;
    private int size;

    public GameBoard(int size) {
        this.size = size;
        this.board = new int[size][size];
    }

    public void initializeGameBoard() {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = random.nextInt(5) + 1;
            }
        }
        board[0][0] = -1;
        board[size - 1][size - 1] = 0;
    }

    public void printBoard() {
        System.out.println("Game Board:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf("%2d", board[i][j]);
            }
            System.out.println();
        }
    }

    public void capturePoint(int row, int col, Player player) {
        if (isValidCoordinate(row, col)) {
            int valueToCapture = board[row][col];
            if (valueToCapture > player.getPeasants()) {
                System.out.println("Not enough peasants to capture the point.");
                player.playerTurn(this, Game.scanner);
            } else if (isAdjacentToPlayerTerritory(row, col)) {
                if (valueToCapture != 0 && valueToCapture != -1) {
                    player.setPeasants(player.getPeasants() - valueToCapture);
                    board[row][col] = 0;
                    System.out.println("Point captured!");
                }
            } else {
                System.out.println("You can only capture adjacent points.");
                player.playerTurn(this, Game.scanner);
            }
        } else {
            System.out.println("Invalid coordinates.");
            player.playerTurn(this, Game.scanner);
        }
    }

    public void captureAdjacentPointForOpponent(int captureValue, Opponent opponent) {
        ArrayList<int[]> adjPoints = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == captureValue) {
                    adjPoints.addAll(getAdjacentPoints(i, j));
                }
            }
        }
        Random random = new Random();
        if (adjPoints.size() > 0) {
            int[] point = adjPoints.get(random.nextInt(adjPoints.size()));
            if (board[point[0]][point[1]] > opponent.getPeasants()) {
                return;
            }
            opponent.setPeasants(opponent.getPeasants() - board[point[0]][point[1]]);
            board[point[0]][point[1]] = captureValue;
        }
    }

    public int calculateTerritoryControl(int controlValue) {
        int controlCount = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == controlValue) {
                    controlCount++;
                }
            }
        }
        return controlCount * 100 / (size * size);
    }

    private boolean isValidCoordinate(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    private boolean isAdjacentToPlayerTerritory(int row, int col) {
        return row > 0 && board[row - 1][col] == 0 || row < size - 1 && board[row + 1][col] == 0 ||
                col > 0 && board[row][col - 1] == 0 || col < size - 1 && board[row][col + 1] == 0;
    }

    private ArrayList<int[]> getAdjacentPoints(int row, int col) {
        ArrayList<int[]> adjPoints = new ArrayList<>();
        if (row > 0) adjPoints.add(new int[]{row - 1, col});
        if (row < size - 1) adjPoints.add(new int[]{row + 1, col});
        if (col > 0) adjPoints.add(new int[]{row, col - 1});
        if (col < size - 1) adjPoints.add(new int[]{row, col + 1});
        return adjPoints;
    }
}
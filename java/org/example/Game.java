package org.example;
import java.io.*;
import java.util.Scanner;
public class Game implements Serializable {
    public static final Scanner scanner = new Scanner(System.in);
    private static final int BOARD_SIZE = 5;
    private static final int WIN_THRESHOLD = BOARD_SIZE * BOARD_SIZE / 2;

    private GameBoard gameBoard;
    private Player player;
    private Opponent opponent;
    private int day;

    public Game()  {
        this.gameBoard = new GameBoard(BOARD_SIZE);
        this.player = new Player();
        this.opponent = new Opponent();
        this.day = 1;

        gameBoard.initializeGameBoard();
    }

    public void playGame() {
        while (player.calculateTerritoryControl(gameBoard) < 50 && opponent.calculateTerritoryControl(gameBoard) < 50 && player.getPeasants() != 0) {
            System.out.println("\n------ Player's Turn ------");
            printGameStatus();
            player.playerTurn(gameBoard, scanner);
            opponent.opponentTurn(gameBoard);

            day++;
            player.endDay();
            opponent.endDay();
        }

        if (player.calculateTerritoryControl(gameBoard) >= 50) {
            System.out.println("Congratulations! You have won the game!");
        } else {
            System.out.println("Game over. Opponent has won.");
        }
    }

    public void printGameStatus() {
        System.out.println("Day " + day);
        System.out.println(player);
        System.out.println(opponent);
        gameBoard.printBoard();
    }

    public static void main(String[] args) {
        Game game = new Game();
        GameUtils.manual();

        if (GameUtils.getUserChoice()) {
            game.playGame();
        } else {
            Game loadedGame = GameUtils.loadSavedGame();
            if (loadedGame != null) {
                loadedGame.playGame();
            } else {
                game.playGame();
            }
        }
    }
}

package org.example;
import java.io.Serializable;
import java.util.Scanner;
public class Player implements Serializable {
    private int peasants;
    private int waterSupply;
    private int riceSupply;
    private int homes;

    public Player() {
        this.peasants = 10;
        this.waterSupply = 0;
        this.riceSupply = 10;
        this.homes = 0;
    }

    public void playerTurn(GameBoard gameBoard, Scanner scanner) {
        System.out.println("Your move:\n" +
                "1. Get some water\n" +
                "2. Pour over the rice\n" +
                "3. Build a house\n" +
                "4. Capture the territory\n" +
                "5. Save the game");

        int choice = GameUtils.getIntInput("Enter your choice: ", scanner);
        switch (choice) {
            case 1:
                collectWater();
                break;
            case 2:
                plantRice();
                break;
            case 3:
                buildHouse();
                break;
            case 4:
                captureTerritory(gameBoard, scanner);
                break;
            case 5:
                GameUtils.saveGame(GameUtils.getFileName(scanner), this);
                break;
            default:
                System.out.println("Invalid choice. Please select again.");
                playerTurn(gameBoard, scanner);
                break;
        }
    }

    private void collectWater() {
        waterSupply += peasants;
    }

    private void plantRice() {
        riceSupply += waterSupply;
        waterSupply = 0;
    }

    private void buildHouse() {
        if (riceSupply >= 1 && waterSupply >= 1 && peasants >= 1) {
            riceSupply -= 1;
            waterSupply -= 1;
            peasants -= 1;
            homes += 1;
        } else {
            System.out.println("Not enough resources to build a house.");
        }
    }

    private void captureTerritory(GameBoard gameBoard, Scanner scanner) {
        System.out.println("Enter the coordinates of the point to capture (row and column separated by a space):");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        gameBoard.capturePoint(row - 1, col - 1, this);
    }

    public void endDay() {
        riceSupply += peasants;
        peasants += homes;
    }

    public int calculateTerritoryControl(GameBoard gameBoard) {
        return gameBoard.calculateTerritoryControl(0);
    }

    @Override
    public String toString() {
        return "Player Peasants: " + peasants +
                "\nPlayer Homes: " + homes +
                "\nWater Supply: " + waterSupply +
                "\nRice Supply: " + riceSupply;
    }

    // Getters and setters

    public int getPeasants() {
        return peasants;
    }

    public void setPeasants(int peasants) {
        this.peasants = peasants;
    }
}

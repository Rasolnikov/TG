package org.example;
import java.io.Serializable;
import java.util.Random;

public class Opponent implements Serializable {
    private int peasants;
    private int waterSupply;
    private int riceSupply;
    private int homes;

    public Opponent() {
        this.peasants = 1;
        this.waterSupply = 10;
        this.riceSupply = 10;
        this.homes = 0;
    }

    public void opponentTurn(GameBoard gameBoard) {
        int choice;
        if (peasants >= 5) {
            choice = 4;
        } else if (waterSupply >= 1 && riceSupply >= 1 && peasants >= 1) {
            choice = 3;
        } else if (waterSupply <= 0) {
            choice = 1;
        } else {
            choice = 2;
        }

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
                captureTerritory(gameBoard);
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
        riceSupply -= 1;
        waterSupply -= 1;
        peasants -= 1;
        homes += 1;
    }

    private void captureTerritory(GameBoard gameBoard) {
        gameBoard.captureAdjacentPointForOpponent(-1, this);
    }

    public void endDay() {
        riceSupply += peasants;
        peasants += homes;
    }

    public int calculateTerritoryControl(GameBoard gameBoard) {
        return gameBoard.calculateTerritoryControl(-1);
    }

    @Override
    public String toString() {
        return "Opponent Peasants: " + peasants +
                "\nOpponent Homes: " + homes +
                "\nOpponent Water Supply: " + waterSupply +
                "\nOpponent Rice Supply: " + riceSupply;
    }

    // Getters and setters

    public int getPeasants() {
        return peasants;
    }

    public void setPeasants(int peasants) {
        this.peasants = peasants;
    }
}

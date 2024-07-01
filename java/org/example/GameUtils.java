package org.example;

import java.io.*;
import java.util.Scanner;

public class GameUtils {
    public static void manual() {
        System.out.println("Game Manual: ");
        System.out.println("Your objective is to capture more than 50% of the game board.");
        System.out.println("Each player has several peasants, houses, and resources such as water and rice.");
        System.out.println("Players take turns performing actions to manage resources and capture territories.");
    }

    public static boolean getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to start a new game or load a saved game?");
        System.out.println("1. Start new game");
        System.out.println("2. Load saved game");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return choice == 1;
    }

    public static Game loadSavedGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the file name to load the game: ");
        String fileName = scanner.nextLine();
        return loadGame(fileName);
    }

    public static void saveGame(String fileName, Object gameState) {
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(gameState);
            System.out.println("Game saved successfully!");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static Game loadGame(String fileName) {
        Game game = null;
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            game = (Game) in.readObject();
            System.out.println("Game loaded successfully!");
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
        return game;
    }

    public static int getIntInput(String prompt, Scanner scanner) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print(prompt);
        }
        return scanner.nextInt();
    }

    public static String getFileName(Scanner scanner) {
        System.out.print("Enter the file name: ");
        return scanner.next();
    }
}
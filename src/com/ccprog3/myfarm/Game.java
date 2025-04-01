/** This is to certify that this project is my own work, based on my personal efforts in studying
* and applying the concepts learned. I have constructed the functions and their respective
* algorithms and corresponding code by myself. The program was run, tested, and
* debugged by my own efforts. I further certify that I have not copied in part or whole or
* otherwise plagiarized the work of other students and/or persons.
* John Joseph F. Giron, 12198129
 */
package com.ccprog3.myfarm;

import com.ccprog3.myfarm.model.Crop;
import com.ccprog3.myfarm.model.Ground;
import com.ccprog3.myfarm.model.Player;

import javax.swing.*;
import java.io.*;
import java.text.FieldPosition;
import java.util.Random;
import java.util.Scanner;


/**
 * The main game class with all game elements present. This is where
 * all the cool stuff happen. 
 * @author John Joseph F. Giron
 */

public class Game {
    private Ground[][] board;
    private Player playerOne;
    private int day;
    private int width, height;
    private int countFreeGround = 0;
    private int maxGround = 0;
    private final String nameCoin = "Objectcoin";
    private int loseCondition = 0; // a bit map
    
    public static final int NOCOINS = 0;
    public static final int GROUNDFULL = 1;

    /**
     * Game constructor with specific board size and starting
     * coins.
     * @param boardWidth Board width
     * @param boardHeight Board height
     * @param coins Starting number of coins
     */
    public Game(int boardWidth, int boardHeight, float coins) {
        this.playerOne  = new Player(coins, nameCoin);
        this.width = boardWidth;
        this.height = boardHeight;
        this.board = new Ground[this.height][this.width];
        this.day = 0;

        String rockFilename = "rockGenerator.csv";
        String rockString = "";
        BufferedReader rockRead;
        
        try {
            rockRead = new BufferedReader(new FileReader(rockFilename));
            String read;
            while ((read = rockRead.readLine()) != null)
                rockString = rockString + read + "\n";
            rockRead.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        createBoard();

        spawnRocks();
//        spawnRocks(rockString);
//        spawnAllRocks();
    }


    /** 
     * Game constructor with specific board size
     * @param boardWidth Board width
     * @param boardHeight Board height
     */
    public Game(int boardWidth, int boardHeight) {
        this(boardWidth, boardHeight, 100);
    }
    
    /**
     * Default game constructor with default settings of the game:
     * <br>10 x 5 board dimension.
     * <br>100 coins
     */
    public Game() {
        this(10, 5, 100);
    }

    /**
     * Advances the game's day. The crops ages when this function is invoked.
     */
    public void nextDay() {
        this.day += 1;
        for (int i = 0; i < height; i++) {
            for (Ground g : board[i]) {
                if (g.getSeed() == null) {
                    continue;
                }
                g.getSeed().age();
            }
        }
        System.out.println("Today is the new day. <3");
    }
    
    /**
     * Plants a seed. It takes the ground object and the
     * seed object.
     * @param ground References to the game's already made ground
     * @param seed The chosen seed created in the Action class
     */
    public void plant(Ground ground, Crop seed) {
        if (ground.plant(seed)) {
            countFreeGround -= 1;
        }
    }
    
    /**
     * Removes the crop/seed in the given ground. Just sets the Crop field
     * of the ground to null. This is used in harvesting and shoveling.
     * @param ground The ground of the crop.
     */
    public void removeCrop(Ground ground) {
        if (ground.removeCrop()) {
            countFreeGround += 1;
        }
    }
    
    /**
     * Removes the ground's crop or the rock, and clears the plow status.
     */
    public void clearBoard() {
        countFreeGround = 0;
        
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.board[i][j].clear();
                countFreeGround += 1;
            }
        }
    }
    
    private void createBoard() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.board[i][j] = new Ground(i, j);
                countFreeGround += 1;
                maxGround += 1;
            }
        }
    }
    
    private boolean checkGrowingCrops() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (checkCropPresence(this.board[i][j]) && 
                    !this.board[i][j].getSeed().isWithered()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Checks if the ground has a seed/crop. Useful for rock spawning.
     * @param ground
     * @return Returns true if there is a seed/crop. Also returns to true
     * for the withered crop. Returns false if the ground is empty or has a rock.
     */
    public boolean checkCropPresence(Ground ground) {
        return (ground.getSeed() != null);
    }
        
    /**
     * A 2D array of the ground.
     * @return A 2D array of the ground.
     */
    public Ground[][] getBoard() {
        return board;
    }

    /**
     * This is the actual "game over".
     * @return returns true if the game meets
     * all the conditions (e.g. no more free ground)
     * to end the game.
     */
    public boolean checkGameOver() {
        boolean noMoney;
        boolean groundFull;
        
        noMoney = playerOne.getCoins() < 7;
        if (noMoney) {
            System.out.println("NO MONEY");
//            JOptionPane.showMessageDialog(null, "NO MONEY");
        }
        groundFull = !checkFreeGround() && !checkGrowingCrops();
        if (groundFull) {
//            JOptionPane.showMessageDialog(null, "GROUND FULL");
            System.out.println("GROUND FULL");
        }
        
        loseCondition = BitMap.setBit(loseCondition, NOCOINS, noMoney);
        loseCondition = BitMap.setBit(loseCondition, GROUNDFULL, groundFull);
        
        return (loseCondition == 3); // 3 in binary is 11
    }
    
    /**
     * Resets the bitmap lose condition.
     */
    public void resetLoseCondition() {
        loseCondition = 0;
    }
    
    /**
     * The main player. For player's data manipulation.
     * @return Returns the game's player object
     */
    public Player getPlayer() {
        return playerOne;
    }
    
    /**
     * The name of the currency. Only the coder can change the name of the 
     * currency. For the MCO purposes, it returns "Objectcoin".
     * @return Returns the String name of the currency.
     */
    public String getNameCoin() {
        return nameCoin;
    }
    
    /**
     * The game's day number. Returns the game's recent day.
     * @return Returns an integer that represents the game's current day.
     */
    public int getDay() {
        return day;
    }
    
    /**
     * Checks if there are still empty ground. Both crops (and
     * withered crops) and rocks must not be there to be counted! Plowed ground
     * is also counted. Used for game over condition checking.
     * @return Returns true if there are still free ground. False if the game
     * runs out of free ground.
     */
    public boolean checkFreeGround() {
        return (countFreeGround != 0);
    }

    /**
     * Spawns rocks. Used the random generator to choose whether to spawn
     * a rock or not for each ground.
     */
    public final void spawnRocks() {
        Random rand = new Random();
        int size = width * height;
        int noOfRocks = 10 + rand.nextInt(30 - 10 + 1);
        int chance = 5;
        
        if (size == 1) {
            return;
        }
        
        while (noOfRocks > 0) {
            for (Ground[] board1 : board) {
                for (int j = 0; j < board[0].length; j++) {
                    if (rand.nextInt(chance) == 0 && noOfRocks > 0) {
                        board1[j].spawnRock();
                        noOfRocks -= 1;
                        countFreeGround -= 1;
                    }
                }
            }
        }
    }

    /**
     * Spawns rocks. Loads the csv file and read it. The 1 means that tile
     * has a rock.
     */
    public final void spawnRocks(String rockString) {
        Character c;
        int row = 0;
        int column = 0;
        for (int i = 0; i < rockString.length(); i++) {
            if (rockString.charAt(i) == ',') {
                System.out.print(",");
                continue;
            }
            else if (rockString.charAt(i) == '\n') {
                System.out.print("\n");
                column = 0;
                row++;
            }
            else {
                System.out.print(rockString.charAt(i));
                if (rockString.charAt(i) == '1') {
                    board[row][column].spawnRock();
                    countFreeGround -= 1;
                }
                column++;
            }
        }
    }

    private void spawnAllRocks() {
        for (Ground[] board1 : board) {
            for (int j = 0; j < board[0].length; j++) {
                board1[j].spawnRock();
                countFreeGround -= 1;
            }
        }
    }
}

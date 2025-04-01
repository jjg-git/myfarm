/** This is to certify that this project is my own work, based on my personal efforts in studying
* and applying the concepts learned. I have constructed the functions and their respective
* algorithms and corresponding code by myself. The program was run, tested, and
* debugged by my own efforts. I further certify that I have not copied in part or whole or
* otherwise plagiarized the work of other students and/or persons.
* John Joseph F. Giron, 12198129
 */
package com.ccprog3.myfarm;

import com.ccprog3.myfarm.model.Crop;
import com.ccprog3.myfarm.model.CropInfo;
import com.ccprog3.myfarm.model.Ground;
import com.ccprog3.myfarm.model.Player;

import javax.swing.*;
import java.util.Random;
import java.util.Scanner;

/**
 * The Action class. This class handles the game data manipulation. This is
 * where the farming process, rock destroying, etc. happen.
 * @author John Joseph F. Giron
 */
public class Action {
    private GameMode mode = GameMode.TERMINAL;
    private final Game currentGame;

    private Terminal terminalGame;
    private GUIControl guiControlGame;
    
    /**
     * Holds the data for the changes by player's farmer title
     */
    public static final int levelUpBenefits[][] = {
        // Left-to-right order
        // 0. Level Requirement
        // 1. Bonus Earnings per Produce
        // 2. Seed Cost Reduction
        // 3. Water Bonus Limit Increase
        // 4. Fertilizer Bonus Limit Increase
        // 5. Registration Fee
        
        // Farmer
        {0, 0, 0, 0, 0, 0},
        
        // Registered Farmer
        {5, 1, 1, 0, 0, 200},
        
        // Distinguished Farmer
        {10, 2, 2, 1, 0, 300},
        
        // Legendary Farmer
        {15, 4, 3, 2, 1, 400}
    };
    
    public static final int LVL_REQ = 0;
    public static final int BONUS_PER_PRODUCE = 1;
    public static final int COST_REDUCT = 2;
    public static final int WATER_BONUS_LIMIT = 3;
    public static final int FERTILIZER_BONUS_LIMIT = 4;
    public static final int REGISTER_FEE = 5;
    
    /**
     * A GameMode enum to either run in Terminal or with GUIControl.
     *  GameMode.TERMINAL  run the game in terminal
     *  <br>GameMode.GUIControl run the game with graphic user interface
     */
    public static enum GameMode {
        TERMINAL,
        GUI
    }
    
    /**
     * An Action constructor. Requires a Game object.
     * @param currentGame The main Game object.
     */
    public Action(Game currentGame) {
        this.currentGame = currentGame;
    }
    
    /**
     * Sets the program to Terminal mode. Creates a Terminal object
     * and destroys the terminal object
     * @param input The main's single Scanner object. It must be reusable.
     */
    public void playTerminal(Scanner input) {
        this.mode = GameMode.TERMINAL;
        this.terminalGame = new Terminal(this, input);
        
        this.guiControlGame = null;
        
        this.terminalGame.start();
    }
    
    /**
     * Sets the program to GUIControl mode. Creates a GUIControl object
     * and destroys the terminal object.
     */
    public void playGUI() {
        this.mode = GameMode.GUI;
        this.guiControlGame = new GUIControl(this);
        
        this.terminalGame = null;
    }
    
    /**
     * Buys a seed. It also accounts the changes due to player's farmer
     * title
     * @param seedIdx The integer index of the seed.
     * @param quantity The number of seeds to buy.
     */
    public void buySeed(int seedIdx, int quantity) {
        int cost;
        int benefits = Action.levelUpBenefits[currentGame.getPlayer().getFarmerType()][Action.COST_REDUCT];
        
        cost = quantity * CropInfo.seedCost[seedIdx] - benefits;
        
        currentGame.getPlayer().loseCoins(cost);
        currentGame.getPlayer().addSeed(seedIdx, quantity);
    }
    
    /**
     * Destroys the rock on the given ground. Costs 50 in-game currency.
     * @param ground The ground where the rock is.
     */
    public void pickaxe(Ground ground) {
        currentGame.getPlayer().loseCoins(50);
        ground.destroyRock();
    }
    
    /**
     * Turns the ground to plowed status. This will make the ground ready to
     * plant a seed in.
     * @param ground The ground to be plowed.
     */
    public void plow(Ground ground) {
        ground.plowMe();
        
        currentGame.getPlayer().gainEXP(0.5f);
    }
    
    /**
     * Plants a seed in the ground.
     * @param ground The ground to plant the seed.
     * @param seedType The type of seed
     * <br> Root Crop
     * <br> Flower
     * <br> Fruit Tree
     */
    public void plant(Ground ground, String seedType) {
        Crop seed = new Crop(ground, seedType);
        int x, y;
        x = ground.getRow();
        y = ground.getColumn();
        
        if (!seed.getType().equals("Fruit Tree")) {
            currentGame.plant(ground, seed);
        }
        else {
            switch (checkFreeInArea(x, y, 1)) {
                // The area is free to plant.
                case 0 -> {
                    JOptionPane.showMessageDialog(null, "I planted");
                    currentGame.plant(ground, seed);
                }
                // There are rocks and/or planted crops.
                case 1 -> {
                    String error = """
                            ERROR! There is/are occupied tile(s) in this area! 
                            Check if there are any grids that are not marked
                            '0' around the position you've inputted.""";
                    System.out.println(error);
                    JOptionPane.showMessageDialog(guiControlGame.getGameWindow(), error);
                    return;
                }
                // Going too close to the edge of the board
                case 2 -> {
                    String error = """
                            ERROR! There is/are occupied tile(s) in this area! 
                            Check if there are any grids that are not marked
                            '0' around the position you've inputted.""";
                    System.out.println(error);
                    JOptionPane.showMessageDialog(guiControlGame.getGameWindow(), error);
                    return;
                }
            }
        }
        
        System.out.println(String.format("Planted the %s at [%d, %d]", 
                           seed.getName(), ground.getRow(), ground.getColumn()));
    }
    
    /**
     * Harvests the crop in the ground argument. This also determines the profit
     * of the harvest.
     * @param ground The ground of the crop
     */
    public void harvest(Ground ground) {
        Crop crop = ground.getSeed();
        Random rand = new Random();
        
        /* Example:
           min = 1,   max = 10
           max - min = 10 - 1 = 9
           *exclusive max*
           nextInt(max - min) = [0, 1, 2, ..., (8)]
          
           profit = 1 + (8) = 9   BAD BAD NIGHTMARE NIGHTMARE
           
           *inclusive max*
           nextInt(max - min) = [0, 1, 2, ..., (8)]
           profit = 1 + (8) + 1 = 9 + 1 = 10   YESSS */
        
        int farmerType = currentGame.getPlayer().getFarmerType();
        int difference = crop.getProduceRange()[1] - crop.getProduceRange()[0];
        int randInt = rand.nextInt((difference != 0) ? difference : 1);
        
        int products = crop.getProduceRange()[0] + (randInt + 1);
        int sell = crop.getSellPiece();
        int benefit = Action.levelUpBenefits[farmerType][Action.BONUS_PER_PRODUCE];
        int harvestTotal;
        float waterBonus;
        float fertilizerBonus;
        float finalProfit;
        
        harvestTotal = products * (sell + benefit);
        waterBonus = harvestTotal * 0.2f * (crop.getWaterGiven() - 1);
        fertilizerBonus = harvestTotal * 0.5f * crop.getFertilizerGiven();
        finalProfit = harvestTotal + waterBonus + fertilizerBonus;
        
        // Slight difference for the Flower type cuz prof said flowers
        // are pretty.
        if (crop.getType().equals("Flower")) {
            finalProfit *= 1.1;
        }
        
        System.out.println(String.format("Player got %s x %d!!", 
                crop.getName(), products));
        currentGame.getPlayer().gainCoins(finalProfit);
        
        ground.removeCrop();
        ground.turnToUnPlowed();
    }
    
    /**
     * Turns the ground to unplowed. Also reduces the money by 7
     * for any action outside removing withered crops.
     * @param ground The ground to shovel on.
     */
    public void shovel(Ground ground) {
        currentGame.getPlayer().loseCoins(7);
        if (currentGame.checkCropPresence(ground)) {
            currentGame.removeCrop(ground);
        }
        ground.turnToUnPlowed();
    }
    
    /**
     * Waters the plant. This affects the profit from
     * harvesting the plant.
     * @param ground The ground in which the crop is planted on
     * @param repeat Number of times to water the plant
     */
    public void waterPlant(Ground ground, int repeat) {
        Crop seed = ground.getSeed();
        
        System.out.println(String.format("Watered the %s at [%d, %d] %d time(s).", 
                           seed.getName(), ground.getRow(), ground.getColumn(), repeat));
        
        currentGame.getPlayer().gainEXP(0.5f * (float)repeat);
        
        while (repeat != 0) {
            seed.giveWater(currentGame.getPlayer().getFarmerType());
            repeat -= 1;
        }
        
        System.out.println("");
    }
    
    /**
     * Gives the crop a fertilizer. This affects the profit from
     * harvesting the crop.
     * @param ground The ground in which the crop is planted to be given
     * a fertilizer.
     * @param repeat The number of fertilizer to give the crop.
     */
    public boolean fertilizePlant(Ground ground, int repeat) {
        Crop seed = ground.getSeed();
        int cost = 10 * repeat;
        if (currentGame.getPlayer().getCoins() < cost) {
            return false;
        }

        System.out.println(String.format("Fertilized the %s at [%d, %d] %d time(s).", 
                           seed.getName(), ground.getRow(), ground.getColumn(), repeat));
        
        currentGame.getPlayer().gainEXP(0.5f * (float)repeat);
        currentGame.getPlayer().loseCoins(10 * repeat);
        
        while (repeat != 0) {
            seed.giveFertilizer(currentGame.getPlayer().getFarmerType());
            repeat -= 1;
        }
        
        System.out.println("");
        return true;
    }
    
    /**
     * Promotes the player. Note that there is no condition
     * checking; it directly does the registration. The
     * condition checking takes place in the Terminal and GUIControl class.
     */
    public void register() {
        int type = currentGame.getPlayer().getFarmerType();
        currentGame.getPlayer().loseCoins(levelUpBenefits[type][REGISTER_FEE]);
        currentGame.getPlayer().register();
    }
    
    /**
     * Checks if the player is ready to register or promote to
     * the next farmer type. It checks for the level requirement and 
     * the cost to register/promote. This will be appeared in the Terminal and
     * GUIControl class
     * @return Returns true if the player is ready to register/promote.
     */
    public boolean legibleToRegister() {
        Player player = currentGame.getPlayer();
        int nextFarmerType = player.getFarmerType() + 1;
        boolean levelMeet;
        boolean moneyMeet;
                
        levelMeet = (player.getLevel() >= Action.levelUpBenefits[nextFarmerType][Action.LVL_REQ]);
        moneyMeet = (player.getCoins() >= Action.levelUpBenefits[nextFarmerType][Action.REGISTER_FEE]);
        
        return !(!moneyMeet || !levelMeet);
    }
    
    /**
     * Returns the mode of the game.
     * @return A GameMode enum in which the game is running.
     */
    public GameMode getMode() {
        return mode;
    }
    
    /**
     * A getter function to refer to the running game.
     * @return The main game.
     */
    public Game getGame() {
        return currentGame;
    }
    
    private int checkFreeInArea(int centerX, int centerY, int radius) {
        int leftSideX = centerX - radius;
        int rightSideX = centerX + radius;
        int topSideY = centerY - radius;
        int bottomSideY = centerY + radius;
        
        int width = currentGame.getBoard()[0].length;
        int height = currentGame.getBoard().length;
        
        // check if there is any out-of-bounds
        if (leftSideX < 0 ||  rightSideX > width ||
             topSideY < 0 || bottomSideY > height  ) {
            return 2;
        }
        
        for (int y = topSideY; y <= bottomSideY; y++) {
            for (int x = leftSideX; x <= rightSideX; x++) {
                if ( currentGame.getBoard()[y][x].getSeed() != null &&
                    !currentGame.getBoard()[y][x].getHasRock()) {
                    return 1;
                }
            }
        }
        
        return 0;
    }
    
}

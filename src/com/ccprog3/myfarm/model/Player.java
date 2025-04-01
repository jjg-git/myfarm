/** This is to certify that this project is my own work, based on my personal efforts in studying
* and applying the concepts learned. I have constructed the functions and their respective
* algorithms and corresponding code by myself. The program was run, tested, and
* debugged by my own efforts. I further certify that I have not copied in part or whole or
* otherwise plagiarized the work of other students and/or persons.
* John Joseph F. Giron, 12198129
 */
package com.ccprog3.myfarm.model;

/**
 * The Player class. The Player class contains the following fields: coins,
 * EXP, level, farmer type, and seed inventory. This also contains mostly
 * getters and setters methods of each fields. 
 * @author John Joseph F. Giron
 */
public class Player {
    private float coins;
    private float defaultCoins;
    private String coinName;
    private float exp = 0.0f;
    private int level = 0;
    private int seedInventory[];
    private int farmerType = 0;
    
    public static final String farmerTypeString[] = {
        "Farmer",
        "Registered Farmer",
        "Distinguished Farmer",
        "Legendary Farmer"
    };
    
    public static final int FARMER = 0;
    public static final int REGISTERED = 1;
    public static final int DISTINGUISHED = 2;
    public static final int LEGENDARY = 3;
    
    /**
     * The constructor. Takes the starting amount of money in the game start,
     * and the name of the currency.
     * @param coins The starting amount of money. It is set at the game's
     * constructor.
     * @param coinName The name of the currency.
     */
    public Player(float coins, String coinName) {
        this.coins = coins;
        this.coinName = coinName;
        this.defaultCoins = coins;
        this.seedInventory = new int[CropInfo.seedList.length];
        
        for (int i = 0; i < this.seedInventory.length; i++) {
            this.seedInventory[i] = 0;
        }
        
        clearSeedInventory();
    }
    
    /**
     * Adds expGain to the player's EXP field.
     * @param expGain The amount of EXP to be added to the EXP stats.
     */
    public void gainEXP(float expGain) {
        this.exp += expGain;
        System.out.println(String.format("Player gains %.1f EXP!!", expGain));
        
        if (levelUpCondition()) {
            this.level += 1;
            System.out.println(String.format("Player levels up to LVL %d!!", level));
        }
    }
    
    /**
     * Raised the player's level.
     */
    public void levelUp() {
        this.level += 1;
    }
    
    /**
     * The earned money from harvesting. This is for integer arguments.
     * @param coinsEarned The integer representation of earned amount of money.
     */
    public void gainCoins(int coinsEarned) {
        System.out.println(String.format("Player earns %d %s!!", coinsEarned, 
                                         coinName));
        this.coins += (float)coinsEarned;
    }
    
    /**
     * The earned money from harvesting. This is for floating-point arguments.
     * @param coinsEarned The floating-point representation of earned amount of money.
     */
    public void gainCoins(float coinsEarned) {
        System.out.println(String.format("Player earns %.2f %s!!", coinsEarned, 
                                         coinName));
        System.out.println("Coins = " + coins);
        this.coins += coinsEarned;
    }
    
    /**
     * The player pays an amount of money. Reduces the player's money. This
     * only accepts integer argument.
     * @param coinsLost The integer representation of payed amount of money.
     */
    public void loseCoins(int coinsLost) {
        System.out.println(String.format("Player pays %d %s!!", coinsLost, 
                                         coinName));
        System.out.println("Coins = " + coins);
        this.coins -= (float)coinsLost;
    }
    
    /**
     * The player pays an amount of money. Reduces the player's money. This
     * only accepts float argument.
     * @param coinsLost The floating-point representation of payed amount of 
     * money.
     */
    public void loseCoins(float coinsLost) {
        System.out.println(String.format("Player pays %.2f %s!!", coinsLost, 
                                         coinName));
        this.coins -= coinsLost;
    }

    /**
     * Returns the player's EXP stat.
     * @return Returns the player's EXP stat.
     */
    public float getEXP() {
        return this.exp;
    }
    
    /**
     * Returns the player's level. This is not the farmer status. They
     * are separate concept.
     * @return Returns the player's level.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Returns the player's amount of money held.
     * @return Returns the player's amount of money held.
     */
    public float getCoins() {
        return this.coins;
    }

    /**
     * Returns an array that represents the player's seed inventory.
     * @return Returns an array that represents the player's seed inventory.
     */
    public int[] getSeedInventory() {
        return seedInventory;
    }
    
    private boolean levelUpCondition() {
        float nextLevel = (float)level + 1.0f;
        
        /* e.g. at level 2, the exp required
           to level up is 300.*/
        float expRequired = nextLevel * 100.0f;
        
        return exp > expRequired;
    }
    
    /**
     * Resets all of the player's data.
     */
    public void resetStats() {
        this.coins = this.defaultCoins;
        this.exp = 0.0f;
        this.level = 0;
        
        clearSeedInventory();
    }
    
    /**
     * Adds one seed to the player's inventory. Each index of the
     * seed inventory array represents the seed in order:
     * <br> 0 - Turnip
     * <br> 1 - Carrot
     * <br> 2 - Potato
     * <br> 3 - Rose
     * <br> 4 - Turnip Flower
     * <br> 5 - Sunflower
     * <br> 6 - Mango
     * <br> 7 - Apple
     * @param idx the index representation of the seed
     * @param quantity the number of seed to be added to the inventory
     */
    public void addSeed(int idx, int quantity) {
        System.out.println(String.format("Player acquired %d %s!", 
                           quantity,
                           CropInfo.seedList[idx]));
        
        this.seedInventory[idx] += quantity;
    }
    
    /**
     * Takes one seed from the player's inventory. Each index of the
     * seed inventory array represents the seed in order:
     * <br> 0 - Turnip
     * <br> 1 - Carrot
     * <br> 2 - Potato
     * <br> 3 - Rose
     * <br> 4 - Turnip Flower
     * <br> 5 - Sunflower
     * <br> 6 - Mango
     * <br> 7 - Apple
     * @param idx the index representation of the seed
     */
    public void useSeed(int idx) {
        System.out.println(String.format("Player took a %s seed!", 
                           CropInfo.seedList[idx]));
        this.seedInventory[idx] -= 1;
    }
    
    private void clearSeedInventory() {
        for (int i = 0; i < this.seedInventory.length; i++) {
            this.seedInventory[i] = 0;
        }
    }
    
    /**
     * Promotes the player to the next farmer type.
     */
    public void register() {
        if (farmerType < Player.LEGENDARY) {
            farmerType += 1;
        }
    }
    
    /**
     * Returns the player's farmer type status.
     * @return Returns an integer representation of the player's farmer type
     * status.
     */
    public int getFarmerType() {
        return farmerType;
    }
    
    /**
     * Checks if the player reaches the top farmer status.
     * @return Returns true if the player reaches the top farmer status.
     */
    public boolean reachedApex() {
        return farmerType == LEGENDARY;
    }
}

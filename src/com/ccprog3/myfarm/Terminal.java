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
import com.ccprog3.myfarm.terminalinterface.IntegerInput;
import com.ccprog3.myfarm.terminalinterface.GridInput;
import com.ccprog3.myfarm.terminalinterface.MultiChoice;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author John Joseph F. Giron
 */
public class Terminal {
    private boolean running = false;
    private boolean gameEnd = false;

    private final Scanner input;
    private final Action action;
    
    private final Game currentGame;
    
    MultiChoice confirmChoice;
    
    MultiChoice toolMenu;
    MultiChoice actionMenu;
    MultiChoice shopSeed;
    
    MultiChoice hoeChoices;
       
    GridInput tileGrid;
    MultiChoice whichCrop;
    
    IntegerInput waterInput;
    IntegerInput fertilizeInput;
    IntegerInput seedQuantity;
    
    public Terminal(Action action, Scanner input) {
        this.action = action;
        this.input = input;
        
        this.currentGame = action.getGame();
        
        this.confirmChoice = new MultiChoice(input, true, "Confirm?", "Yes");
        this.tileGrid = new GridInput(input, 
                                           currentGame.getBoard()[0].length, // width  (column)
                                           currentGame.getBoard().length,    // height (row)
                                           "Which tile?");
        this.whichCrop = new MultiChoice(input, false, "Which plant?");

                
        this.actionMenu = new MultiChoice(input, false, "What to do?", 
                                                 "Use Tools (Plowing Tool, Shovel, etc.)",
                                                 "Go to sleep (advance to next day)",
                                                 "Buy seeds",
                                                 "Promote yourself (Register in MCO termss)",
                                                 "Quit game");
        this.toolMenu = new MultiChoice(input, true, "Tools:",
                                                 "Hoe (plow and harvest)",
                                                 "Pickaxe (destroy rocks)",
                                                 "Shovel (remove withered crops)",
                                                 "Watering can (water the crop)",
                                                 "Fertilizer (fertilize the crop)");
        
        this.shopSeed = new MultiChoice(input, true, "Shop now! Our seeds are *world-class*");
//        for (String seeds : CropInfo.seedList) {
        for (int i = 0; i < CropInfo.seedList.length; i++) {
            this.shopSeed.addAnswer(String.format("%s (%d %s)", 
                                    CropInfo.seedList[i], 
                                    CropInfo.seedCost[i], 
                                    currentGame.getNameCoin()));
        }
        
        this.hoeChoices = new MultiChoice(input, true, """
                                                       The hoe tool can be used to plow the unplowed
                                                       soil and harvest crops. What are you going to do?""",
                                          "Plow the ground",
                                          "Plant a seed",
                                          "Harvest a crop");
        
        this.waterInput = new IntegerInput(input, "Number of times to water this plant.", 7);
        this.fertilizeInput = new IntegerInput(input, "Number of times to fertilize this plant.", 7);
        
        this.seedQuantity = new IntegerInput(input, "Quanity: ");
        
    }
    
    // [02C01] start()
    /**
     * Starts the game.
     */
    public void start() {
        System.out.println("Game start!");
        this.running = true;
        
        loop();
    }
    
    // [02C02] loop()
    /**
     * Performs any game functions in every loop step.
     */
    public void loop() {
        MultiChoice mainMenu = new MultiChoice(input, false, 
                """
                Welcome to MyFarm, a farming simulation game! An addicting
                game that will slice your lifespan in half!
                
                Major Course Output Phase 1
                By: John Joseph F. Giron""",
                "Start game",
                "End game");
                
        while (mainMenu.getAnswer() != 2) {
            mainMenu.invoke();
            gameEnd = (mainMenu.getAnswer() == 1);
            
            while (gameEnd) {
                gameEnd = gameLoop();
            }
            
            System.out.println("");
            System.out.println("GAME OVER!!");
            System.out.println("");
            
            currentGame.getPlayer().resetStats();
            currentGame.clearBoard();
            currentGame.resetLoseCondition();
            currentGame.spawnRocks();
        }
        
        quit();
    }
    
    public boolean gameLoop() {
        if (currentGame.checkGameOver()) {
            return false;
        }
        
        System.out.println("");
        gameStatus();
        
        System.out.println("");
        
        while (!actionMenu.invoke()) {}
        System.out.println("");
        
        switch (actionMenu.getAnswer()) {
            // Tool menu
            case 1 -> {
                while (!toolMenu.invoke()) {}
                System.out.println("");
                
                if (toolMenu.getAnswer() != 0) {
                    toolPerform();
                }
            }
            
            // Go to sleep
            case 2 -> {
                useBed();
            }
            
            // Shoppee delivery
            case 3 -> {
                while (!shopSeed.invoke()) {}
                System.out.println("");
                
                if (shopSeed.getAnswer() != 0) {
                    playerBuysSeed();
                }
            }
            
            // Register
            case 4 -> {
                playerRegister();
            }
        }
        
        System.out.println("");
        
        return (actionMenu.getAnswer() != actionMenu.getSize());
    }
    
    private void toolPerform() {
        switch (toolMenu.getAnswer()) {
            case 1 -> {
                useHoe();                
            }
            
            case 2 -> {
                usePickAxe();
            }
            
            case 3 -> {
                useShovel();
            }
            
            case 4 -> {
                useWaterCan();
            }
            
            case 5 -> {
                useFertilizer();
            }
        }
    }
    
    private void playerBuysSeed() {
        int idx = shopSeed.getAnswer() - 1;
        int quantity;
        int cost;
        int benefits = Action.levelUpBenefits[currentGame.getPlayer().getFarmerType()][Action.COST_REDUCT];
        
        while (!seedQuantity.invoke()) {}
        System.out.println("");
        
        quantity = seedQuantity.getAnswer();
        cost = quantity * (CropInfo.seedCost[idx] - benefits);
        
        if (cost > currentGame.getPlayer().getCoins()) {
            System.out.println("ERROR! Insufficient number of coins!");
            return;
        }
        
        action.buySeed(idx, quantity);
    }
    
    /**
     * Ends the *program*.
     */
    private void quit() {
        System.out.println("Game end!");
        this.running = false;
    }
    
    /**
     * Used for the while loop in the driver class
     * @return Returns a Boolean value whether or not the 
     * game is running.
     */
    public boolean isRunning() {
        return this.running;
    }
    
    private void useHoe() {
        Ground ground;
        int x, y;
        
        while (!hoeChoices.invoke()) {}
        if (hoeChoices.getAnswer() == 0) {
            // Go back to action menu
            return;
        }
        
        showBoard();
        while (!tileGrid.invoke()) {}
        System.out.println("");
        
        x = tileGrid.getX();
        y = tileGrid.getY();
        ground = currentGame.getBoard()[y][x];
        
        if (ground.getHasRock()) {
            
        }
        
        switch (hoeChoices.getAnswer()) {
            case 1 -> doPlowing(ground);
            case 2 -> doPlanting(ground);
            case 3 -> doHarvesting(ground);
        }
    }
    
    private void doPlowing(Ground ground) {
        if (currentGame.checkCropPresence(ground)) {
            System.out.println("""
                               ERROR! There is already a crop!""");
            return;
        }
        if (ground.isPlowed()) {
            System.out.println("""
                               ERROR! This ground is already plowed!!""");
            return;
        }
        if (ground.getHasRock()) {
            System.out.println("""
                               ERROR! There is a rock!""");
            return;
        }
        action.plow(ground);
        showBoard();
        
    }
    
    private void doPlanting(Ground ground) {
        int idxOfChosenSeed;
        String seedType;
        
        if (seedBagList().isEmpty()) {
            System.out.println("""
                               ERROR! You don't have a seed yet. Go back to the action menu
                               and buy some seedsssss.""");
            return;
        }
        else if (!ground.isPlowed()) {
            System.out.println("""
                               ERROR! This ground is not plowed yet! Go back to
                               the \"Hoe\" option and choose \"Plow the 
                               ground\"""");
            return;
        }
        
        updateSeedBag();
        
        while (!whichCrop.invoke()) {}
        System.out.println("");
                
        idxOfChosenSeed = whichCrop.getAnswer() - 1;
        
        seedType = CropInfo.seedList[idxOfChosenSeed];
        
        currentGame.getPlayer().useSeed(idxOfChosenSeed);
        updateSeedBag();
        
        action.plant(ground, seedType);
        showBoard();
        
    }
    
    private void doHarvesting(Ground ground) {
        // Check if there is a seed.... SEEEEEDDDDD ehhhh
        if (!currentGame.checkCropPresence(ground)) {
            System.out.println("ERROR! That ground has no crop!!");
            return;
        }
        
        if (!ground.getSeed().isHarvestable()) {
            System.out.println("""
                               ERROR! That crop is not ready to be harvested!
                               Wait until it is fully grown.""");
            return;
        }
        
        if (ground.getSeed().isWithered()) {
            System.out.println("""
                               ERROR! That crop is left for too long...""");
            return;
        }
        
        action.harvest(ground);
        showBoard();
        
    }
    
    private void usePickAxe() {
        Ground ground = pickAGround();
        if (!ground.getHasRock()) {
            System.out.println(String.format("ERROR! No rock here at (%d, %d)!",
                                ground.getRow(), ground.getColumn())
            );
            return;
        }
        if (currentGame.getPlayer().getCoins() < 50) {
            System.out.println(String.format(
                    """
                    ERROR! You need at least 50 %s to destroy a rock. Didn't know
                    your stamina is tied to your money.""", 
                    currentGame.getNameCoin())
            );
            return;
        }
        action.pickaxe(pickAGround());
        showBoard();
        
    }

    private void useShovel() {
        Ground ground;
                
        ground = pickAGround();
        
        if (ground.getHasRock()) {
            System.out.println("""
                               ERROR! There's a rock! You can't possibly
                               destroy a rock with a shovel.""");
            return;
        }
        
        if (!ground.isPlowed() || currentGame.checkCropPresence(ground)) {
            if (!ground.getSeed().isWithered()) {
                System.out.println(String.format("""
                                    WARNING! It costs 7 %s to shovel on a ground that is
                                    not plowed yet nor has a seed.""", 
                                    currentGame.getNameCoin()
                ));

                while (!confirmChoice.invoke()) {}
                if (confirmChoice.getAnswer() == 0) {
                    return;
                }
            }
        }
        
        action.shovel(ground);
        showBoard();
        
    }
    
    private void useWaterCan() {
        Ground ground;
        Crop seed;
        int water;
        int current;
        int max;
              
        ground = pickAGround();
        
        if (!currentGame.checkCropPresence(ground)) {
            System.out.println("""
                               ERROR! No crop there!""");
            return;
        }
        
        seed = ground.getSeed();
        
        if (seed.getWaterPassed() && 
            seed.hasWaterBonus(currentGame.getPlayer().getFarmerType())) {
            System.out.println("""
                               ERROR! This crop has reached its
                               water limit!""");
            return;
        }
        
        while (!waterInput.invoke()) {}
        System.out.println("");
        
        water = waterInput.getAnswer();
        current = ground.getSeed().getWaterGiven();
        max = ground.getSeed().getWaterBonus();
        
        water = Math.min(current + water, max);
        
        action.waterPlant(ground, water);
        
    }

    private void useFertilizer() {
        Ground ground;
        Crop seed;
        
        int fertilizer;
        int current;
        int max;
        
        ground = pickAGround();
              
        if (!currentGame.checkCropPresence(ground)) {
            System.out.println("""
                               ERROR! No crop there!""");
            return;
        }
        
        seed = ground.getSeed();
        
        if (seed.getFertilizerPassed() && 
            seed.hasFertilizerBonus(currentGame.getPlayer().getFarmerType())) {
            System.out.println("""
                               ERROR! This crop has reached its
                               fertilizer limit!""");
            return;
        }
        
        while (!fertilizeInput.invoke()) {}
        System.out.println("");
        
        fertilizer = fertilizeInput.getAnswer();
        current = ground.getSeed().getFertilizerGiven();
        max = ground.getSeed().getFertilizerBonus();
        
        fertilizer = Math.min(current + fertilizer, max);
        
        action.fertilizePlant(ground, fertilizer);
        
    }
    
    private void useBed() {
        while (!confirmChoice.invoke()) {}
        System.out.println("");
        
        if (confirmChoice.getAnswer() != 1) {
            return;
        }
        currentGame.nextDay();
        System.out.println("");
        showBoard();
    }
    
    public void playerRegister() {
        int idx = currentGame.getPlayer().getFarmerType();
        
        String status = Player.farmerTypeString[idx];
        String next;
        
        if (currentGame.getPlayer().reachedApex()){
            System.out.println("No next farmer status to promote to. You're"
                    + "\nalready at the top!");
            return;
        }
        
        next = Player.farmerTypeString[idx + 1];
        
        System.out.println(
                String.format("""
                              You are currently the *%s*. It costs %d to become
                              the *%s*, and you must be at LVL %d.\n""", 
                        status, Action.levelUpBenefits[idx + 1][Action.REGISTER_FEE],
                        next, Action.levelUpBenefits[idx + 1][Action.LVL_REQ])
        );
        
        System.out.println(
                String.format("""
                              The benefits are:
                              \t* original seed cost - %d
                              \t* profit of harvest per piece + %d
                              \t* Number of watering for bonus + %d 
                              \t* Number of fertilizeing for bonus %d""",
                              Action.levelUpBenefits[idx + 1][Action.COST_REDUCT],
                              Action.levelUpBenefits[idx + 1][Action.BONUS_PER_PRODUCE],
                              Action.levelUpBenefits[idx + 1][Action.WATER_BONUS_LIMIT],
                              Action.levelUpBenefits[idx + 1][Action.FERTILIZER_BONUS_LIMIT])
        );
        
        while (!confirmChoice.invoke()){}
        
        if (confirmChoice.getAnswer() == 0) {
            return;
        }
        
        if (!action.legibleToRegister()) {
            System.out.println(
                    String.format("You are not ready to promote to %s!", next)
            );
            return;
        }
        System.out.println("Congratulations! You are now " + next + "!");
        action.register();
    }
    
    
    public void showBoard() {
        /*
        output:
          0 1 2 3 4 5 6 7 8 9
         ---------------------
        0|0|0|0|0|0|0|0|0|0|0|
         ---------------------
        1|0|0|0|0|0|0|0|0|0|0|
         ---------------------
        2|0|0|0|0|0|0|0|0|0|0|
         ---------------------
        3|0|0|0|0|0|0|0|0|0|0|
         ---------------------
        4|0|0|0|0|0|0|0|0|0|0|
         ---------------------
        
        // LEGEND
        0 - 4: Soil Type
        A - H: Crop Type
        */
        Ground board[][] = currentGame.getBoard();
        String mark;
        System.out.println("BOARD:");
        
        // Show the column (x coordinate) labels
        System.out.print("  ");
        for (int i = 0; i < board[0].length; i++) {
            System.out.print(i + " ");
        }

        System.out.println("");

        // Row (per "y coordinate")
        for (int i = 0; i < board.length; i++) {
            System.out.print(" ");
            for (Ground item : board[0]) {
                System.out.print("---");
            }
            System.out.println("");
            
            // Show the column (x coordinate) labels
            System.out.print(i + "|");
            
            // Column (per "x coordinate")
            for (int j = 0; j < board[0].length; j++) {
                // It's an empty tile
                mark = " ";
                
                // Check if it is plowed
                if (board[i][j].isPlowed()) {
                    mark = "X";
                }
                
                // Check if there's a seed
                if (board[i][j].getSeed() != null) {
                    mark = String.format("%d", board[i][j].getSeed().getDayPassed());
                    
                    // Check if it is withered
                    if (board[i][j].getSeed().isWithered()) {
                        mark = "W";
                    }
                }
                
                // Check if it's a rock
                if (board[i][j].getHasRock()) {
                    mark = "R";
                }
                
                System.out.print(mark);
                System.out.print("|");
            }
            System.out.println("");
        }
        
        System.out.print(" ");
        for (Ground item : board[0]) {
            System.out.print("---");
        }
        System.out.println("");
        
    }
    
    public void cropStatus() {
        /*
        output:
        a - no. of times watered     b - max no. of times watered
        c - no. of times fertilized  d - max no. of times fertilized
        * - plants with bonuses (keep watering and fertilize!)
        
        Location           Status               Days Passed
        [ x,  y]           Water  Fertilize     * ready to be harvested
                           [a/b]  [c/d] 
        [ 5,  3]   Turnip  [0/1]  [0/0]         0
        [ 5,  4]   Turnip  [1/1]  [0/0]         0
        [ 5,  5]   Turnip  [1/1]  [0/0]         0
        [ 5,  6]   Turnip* [2/1]  [1/0]         2*
        */     
        String gridFormat;
        String nameFormat;
        String waterStatus;
        String fertilizeStatus;
        String daysFormat;
        
        boolean hasCrop = false;
        
        int water, waterMax, fertilize, fertilizeMax;
        
        int farmType = currentGame.getPlayer().getFarmerType();
        
        Crop seed;
        
        System.out.println("""
                           a - no. of times watered     b - max no. of times watered
                           c - no. of times fertilized  d - max no. of times fertilized
                           * - plants with bonuses (keep watering and fertilize!)\n""");
        
        System.out.print("Location");
        System.out.print("           ");
        System.out.print("Status");
        System.out.print("               ");
        System.out.println("Days Passed");
        
        System.out.print("[ x,  y]");
        System.out.print("           ");
        System.out.println("Water  Fertilize     * ready to be harvested");
        
        // Go through all the tiles
        // y or the rows
        for (int i = 0; 
             i < currentGame.getBoard().length; 
             i++) {
            
            // x or the columns
            for (int j = 0;
                 j < currentGame.getBoard()[0].length;
                 j++) {
                
                seed = currentGame.getBoard()[i][j].getSeed();
                
                // Check if there's a plant
                if (seed != null) {
                    hasCrop = true;
                    
                    gridFormat = String.format("[%2d, %2d] ", i, j);
                    System.out.print(gridFormat);
                    
                    if (!seed.isWithered()) {
                        nameFormat = String.format("%8s", seed.getName());
                        System.out.print(nameFormat);

                        if (seed.hasWaterBonus(farmType) || 
                            seed.hasFertilizerBonus(farmType)) {
                            System.out.print("* ");
                        }
                        else {
                            System.out.print("  ");
                        }

                        // Determining the crop's status
                        water = seed.getWaterGiven();
                        waterMax = seed.getWaterNeeds();

                        fertilize = seed.getFertilizerGiven();
                        fertilizeMax = seed.getFertilizerNeeds();

                        waterStatus = String.format("[%d/%d]  ", water, waterMax);
                        fertilizeStatus = String.format("[%d/%d]", fertilize, fertilizeMax);

                        System.out.print(waterStatus + fertilizeStatus);

                        System.out.print("         ");

                        daysFormat = String.format("%d", seed.getDayPassed());
                        if (seed.isHarvestable()) {
                            daysFormat = daysFormat.concat("*");
                        }
                        System.out.println(daysFormat);
                    }
                    else {
                        nameFormat = String.format("%9s", "WITHERED");
                        System.out.print(nameFormat);
                    }
                }
            }
        }
        if (!hasCrop) {
            System.out.println("No crops! Use the \"Plowing tool\" first.");
        }
    }  

    public void playerStatus() {
        float coin = currentGame.getPlayer().getCoins();
        float exp = currentGame.getPlayer().getEXP();
        int level = currentGame.getPlayer().getLevel();
        
        showBoard();
        
        System.out.println("----PLAYER INFO----");
        System.out.println(String.format("Objectcoin = %.2f", coin));
        System.out.println(String.format("LEVEL %3d\t\tEXP = %.2f", level, exp));
        System.out.println("");
        
        if (!seedBagList().isEmpty()) {
            showSeedBag();
        }
        
    }
    
    public void showDay() {
        System.out.println(String.format("----DAY %d----", currentGame.getDay()));
    }
    
    public void gameStatus() {
        showDay();
        System.out.println("");
        
        cropStatus();
        System.out.println("");
        
        playerStatus();
        System.out.println("");
    }
    
    private void updateSeedBag() {
        whichCrop.replaceListAnswer(seedBagList());
    }
    
    private ArrayList<String> seedBagList() {
        ArrayList<String> seedStringList = new ArrayList<>();
        String format;
        Player player = currentGame.getPlayer();
        int seedBag[] = player.getSeedInventory();
        int size = seedBag.length;
        
//        for (int quantity : currentGame.getPlayer().getSeedInventory()) {
        for (int i = 0; i < size; i++) {
            if (seedBag[i] == 0) {
                continue;
            }
            format = String.format("%s x %d", CropInfo.seedList[i], seedBag[i]);
            seedStringList.add(format);
        }
        
        return seedStringList;
    }
    
    private Ground pickAGround () {
        int x, y;
        
        showBoard();
        while (!tileGrid.invoke()) {}
        System.out.println("");
        
        x = tileGrid.getX();
        y = tileGrid.getY();
        
        return currentGame.getBoard()[y][x];
    }
    
    private void showSeedBag() {
        System.out.println("# SEED BAG");
        for (String seeds : seedBagList()) {
            System.out.println(seeds);
        }
    }
}

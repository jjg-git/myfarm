package com.ccprog3.myfarm;

/** This is to certify that this project is my own work, based on my personal efforts in studying
* and applying the concepts learned. I have constructed the functions and their respective
* algorithms and corresponding code by myself. The program was run, tested, and
* debugged by my own efforts. I further certify that I have not copied in part or whole or
* otherwise plagiarized the work of other students and/or persons.
* John Joseph F. Giron, 12198129
 */

import com.ccprog3.myfarm.guinterface.GameWindow;
import com.ccprog3.myfarm.guinterface.GroundButton;
import com.ccprog3.myfarm.guinterface.SeedItem;
import com.ccprog3.myfarm.guinterface.ToolsPanel;
import com.ccprog3.myfarm.model.CropInfo;
import com.ccprog3.myfarm.model.Ground;
import com.ccprog3.myfarm.model.Player;

import javax.swing.*;

/**
 *
 * @author John Joseph F. Giron
 */
public class GUIControl {

    public static boolean debug = false;
    private Action action;
    private GameWindow gameWindow;
    private int mouseStatus = 0;
    private int idxOfSeed = -1;

    public static final int SELECT = 0;
    public static final int PLOW = 1;
    public static final int WATER = 2;
    public static final int FERTILIZE = 3;
    public static final int HARVEST = 4;
    public static final int REMOVE_WITHERED = 5;
    public static final int DESTROY_ROCK = 6;

    public GUIControl(Action action) {
//        System.out.println("hello");
        this.action = action;
        gameWindow = new GameWindow(this);
    }

    public Action getAction() {
        return action;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public int getMouseStatus() {
        mouseStatus = gameWindow.getToolsPanel().getTool();
        return mouseStatus;
    }

    public void interactGround(GroundButton groundButton) {
        int row = groundButton.getRow();
        int column = groundButton.getColumn();

        Ground ground = action.getGame().getBoard()[row][column];

        ToolsPanel toolsPanel = gameWindow.getToolsPanel();
        mouseStatus = toolsPanel.getTool();

//        System.out.println("Grid [" + x + "," + y + "]\n"
//                    + "Status = " + mouseStatus);
        if (action.getGame().checkGameOver()) {
            JOptionPane.showMessageDialog(gameWindow, "GAME OVER!!");
            System.exit(0);
        }
        switch (mouseStatus) {
            case ToolsPanel.HOE -> useHoe(groundButton);
            case ToolsPanel.PICKAXE -> usePickaxe(ground);
            case ToolsPanel.SHOVEL -> {
                useShovel(ground);
            }
        }
        updateGUI();
    }

    private void usePickaxe(Ground ground) {
        int answer = JOptionPane.showConfirmDialog(gameWindow, "Destroying a rock costs 50 " +action.getGame().getNameCoin() +
                "s. Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, new ImageIcon("assets/pickaxe.png"));
        if (answer == JOptionPane.YES_OPTION) {
            if (action.getGame().getPlayer().getCoins() > 50) {
                action.pickaxe(ground);
            }
            else {
                JOptionPane.showMessageDialog(gameWindow,
                        String.format(
                                """
                                ERROR! You need at least 50 %s to destroy a rock. 
                                Didn't know your stamina is tied to your money.""",
                                action.getGame().getNameCoin())
                );
            }
        }
    }

    private void useShovel(Ground ground) {
        int answer = JOptionPane.showConfirmDialog(gameWindow, "Shovelling spends 7 " +action.getGame().getNameCoin() +
                "s. Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, new ImageIcon("assets/shovel.png"));
        if (answer == JOptionPane.YES_OPTION)
            action.shovel(ground);
    }

    public void buySeed(SeedItem item) {
        int idx = item.getSeedIdx();
        int farmerType = action.getGame().getPlayer().getFarmerType();
        int quantity;
        int cost;
        int benefits = Action.levelUpBenefits[farmerType][Action.COST_REDUCT];

        System.out.println("hello");

        quantity = 1;
        cost = quantity * (CropInfo.seedCost[idx] - benefits);

        if (cost > action.getGame().getPlayer().getCoins()) {
            JOptionPane.showMessageDialog(gameWindow,
                    String.format(
                    """
                    ERROR! Insufficient number of coins!""",
                        action.getGame().getNameCoin())
            );
            return;
        }

        action.buySeed(idx, quantity);
        gameWindow.getPlayerInfo().updateInfo(action.getGame());
        gameWindow.getInventoryPanel().updateList(action.getGame().getPlayer());
   }

    private void useHoe(GroundButton groundButton) {
        int row = groundButton.getRow();
        int column = groundButton.getColumn();
        ToolsPanel toolsPanel = gameWindow.getToolsPanel();
        Ground ground = action.getGame().getBoard()[row][column];

        System.out.println("ground button = ["+ row + "][" + column + "]");
        System.out.println("ground = ["+ ground.getRow() + "][" + ground.getColumn() + "]");

        switch (groundButton.getState()) {
            case GroundButton.DRY -> ground.plowMe();
            case GroundButton.PLOWED -> {
                if (ground.getSeed() == null) {
                    doPlanting(ground);
                }
//                JOptionPane.showMessageDialog(gameWindow, "Hello");
            }
            case GroundButton.PLANT -> {
                if (toolsPanel.isFertilize()) {
//                    JOptionPane.showMessageDialog(gameWindow, "Fertilized");
                    // together
                    System.out.println("Fertilize x "
                            + toolsPanel.getFertilizeCount());
                    if (!action.fertilizePlant(ground, toolsPanel.getFertilizeCount())) {
                        JOptionPane.showMessageDialog(gameWindow, String.format("""
                                ERROR! Not enough %ss to fertilize!""", action.getGame().getNameCoin()));
                    }
                }

                if (toolsPanel.isWater()) {
//                    JOptionPane.showMessageDialog(gameWindow, "Watered");
                    // together
                    System.out.println("Water x "
                            + toolsPanel.getWaterCount());
                    action.waterPlant(ground, toolsPanel.getWaterCount());
                }
            }
            case GroundButton.HARVEST -> {action.harvest(ground);}
        }
    }

    public void playerRegister() {
//        JOptionPane.showMessageDialog(gameWindow, "Congratulations");
        int idx = action.getGame().getPlayer().getFarmerType();

        String status = Player.farmerTypeString[idx];
        String next;

        if (action.getGame().getPlayer().reachedApex()){
            JOptionPane.showMessageDialog(gameWindow,
            "No next farmer status to promote to. You're"
                    + "\nalready at the top!");
            return;
        }

        next = Player.farmerTypeString[idx + 1];

        String reminderMessage = String.format("""
                              You are currently the *%s*. It costs %d to become
                              the *%s*, and you must be at LVL %d.\n""",
                status, Action.levelUpBenefits[idx + 1][Action.REGISTER_FEE],
                next, Action.levelUpBenefits[idx + 1][Action.LVL_REQ]);

        JOptionPane.showMessageDialog(gameWindow, reminderMessage);

        if (!action.legibleToRegister()) {
            JOptionPane.showMessageDialog(gameWindow, String.format("You are not ready to promote to %s!", next));
            updateGUI();
            return;
        }

        JOptionPane.showMessageDialog(gameWindow, "Congratulations! You are now " + next + "!");
        action.register();
        updateGUI();
    }

    public void useBed() {
        JOptionPane.showMessageDialog(gameWindow, "Today is the new day. <3");
        action.getGame().nextDay();
        updateGUI();
    }

    public void chooseSeed(int seedIdx) {
        idxOfSeed = seedIdx;
    }

    public void doPlanting(Ground ground) {
//        JOptionPane.showMessageDialog(gameWindow, "im planting");
        String seedType;

        if (idxOfSeed == -1) {
            JOptionPane.showMessageDialog(gameWindow, """
                    Select the seed first in the inventory on
                    the right side!!""");
            return;
        }

        seedType = CropInfo.seedList[idxOfSeed];
        System.out.println(seedType);

        if (action.getGame().getPlayer().getSeedInventory()[idxOfSeed] == 0) {
            JOptionPane.showMessageDialog(gameWindow, """
                               ERROR! You don't have a seed yet. Go back to the shop tab
                               and buy some seedsssss.""");
            return;
        }
        action.getGame().getPlayer().useSeed(idxOfSeed);

        action.plant(ground, seedType);
    }

    public void updateGUI() {
        gameWindow.getGroundPanel().updateGround();
        gameWindow.getPlayerInfo().updateInfo(action.getGame());
        gameWindow.getInventoryPanel().updateList(action.getGame().getPlayer());
    }

    public void updateRegisterBenefits() {
        if (!action.getGame().getPlayer().reachedApex()) {
            gameWindow.getToolsPanel().updateBenefit(action.getGame().getPlayer().getFarmerType() + 1);
        }
    }
}

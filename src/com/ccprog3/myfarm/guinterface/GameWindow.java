/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ccprog3.myfarm.guinterface;

import com.ccprog3.myfarm.*;

import java.awt.BorderLayout;
import javax.swing.*;

/**
 *
 * @author John Joseph F. Giron
 */
public class GameWindow extends JFrame{
        
    private int GAP = 1;
    private int HGAP = GAP;
    private int VGAP = GAP;
    
//    private String assetString = "/com/ccprog3/myfarm/guinterface/assets/";
//    private URL groundIconURL = getClass().getResource(assetString + "dirt.png");
    
    
    // GUIControl elements
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel westPanel;
    private JPanel eastPanel;
    
    private JPanel mainPanel;
    
    private GroundPanel groundPanel;
    private ToolsPanel toolsPanel;
    private ShopPanel shopPanel;
    private StatBar playerInfo;
    private InventoryPanel inventoryPanel;
    
    // Communicating game elements
    private GUIControl guiControl;
    
    public GameWindow(GUIControl guiControl) {
        this.guiControl = guiControl;
        
        mainPanel = new JPanel(new BorderLayout(HGAP, VGAP));
        mainPanel.setVisible(true);
        add(mainPanel);
        
        setupGUI();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("MyFarm");
        setSize(1280, 720);
//        setSize(640, 480);
        setLocationRelativeTo(null);
        
        setVisible(true);
    }
    
    private void setupGUI() {
        groundPanel = new GroundPanel(guiControl);
        shopPanel = new ShopPanel();
        toolsPanel = new ToolsPanel();
        inventoryPanel = new InventoryPanel();
        
        setupBorderPanels();
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        mainPanel.add(eastPanel, BorderLayout.EAST);
        mainPanel.add(westPanel, BorderLayout.WEST);

        
        playerInfo = new StatBar();
        playerInfo.updateInfo(guiControl.getAction().getGame());
        
        inventoryPanel.updateList(guiControl.getAction().getGame().getPlayer());

        shopPanel.setGui(guiControl);
        inventoryPanel.setGUI(guiControl);

        toolsPanel.updateBenefit(guiControl.getAction().getGame().getPlayer().getFarmerType() + 1);
        toolsPanel.setGUI(guiControl);
        
        JTabbedPane centerTab = new JTabbedPane();
        JScrollPane itemList = new JScrollPane(shopPanel);
        itemList.getVerticalScrollBar().setUnitIncrement(16);
        
        centerTab.addTab("Farm land", groundPanel);
        centerTab.addTab("Shop", itemList);
        
        centerTab.setSelectedComponent(itemList);
        
        centerPanel.add(playerInfo);
        centerPanel.add(centerTab);
        westPanel.add(toolsPanel);
        eastPanel.add(inventoryPanel);
    }
    
    private void setupBorderPanels() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, 
                                                 BoxLayout.PAGE_AXIS));
        
        northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, 
                                                 BoxLayout.PAGE_AXIS));
        southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, 
                                                 BoxLayout.PAGE_AXIS));
        westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, 
                                                 BoxLayout.PAGE_AXIS));
        eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, 
                                                 BoxLayout.PAGE_AXIS));
    }

    public GroundPanel getGroundPanel() {
        return groundPanel;
    }

    public ToolsPanel getToolsPanel() {
        return toolsPanel;
    }

    public StatBar getPlayerInfo() {
        return playerInfo;
    }

    public InventoryPanel getInventoryPanel() {
        return inventoryPanel;
    }
}

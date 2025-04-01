/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ccprog3.myfarm.guinterface;

import com.ccprog3.myfarm.GUIControl;
import com.ccprog3.myfarm.model.CropInfo;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author John Joseph F. Giron
 */
public class ShopPanel extends JPanel{    
    private SeedItem buyTurnip;
    private SeedItem buyPotato;
    private SeedItem buyCarrot;
    private SeedItem buyRose;
    private SeedItem buyTurnipFlower;
    private SeedItem buySunflower;
    private SeedItem buyMangoTree;
    private SeedItem buyAppleTree;
    
    public ShopPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        buyTurnip = new SeedItem("Turnip", "Very tasty.", 
                          new ImageIcon("assets/turnip.png"));
        
        buyPotato = new SeedItem("Potato", "Always on a couch.",
                          new ImageIcon("assets/potato.png"));
                                  
        buyCarrot = new SeedItem("Carrot", "For bunny-sonas.",
                          new ImageIcon("assets/carrot.png"));
        
        buyRose = new SeedItem("Rose", "We don't talk about that rose.",
                          new ImageIcon("assets/rose.png"));
        
        buyTurnipFlower = new SeedItem("Turnip Flower", "The inedible one.",
                          new ImageIcon("assets/flower_turnip.png"));
        
        buySunflower = new SeedItem("Sunflower", "We're not in UP Diliman.",
                          new ImageIcon("assets/sunflower.png"));
        
        buyMangoTree = new SeedItem("Mango Tree", "I'm allergic to this.",
                          new ImageIcon("assets/unknown.png"));
        
        buyAppleTree = new SeedItem("Apple Tree", "Mansanas.",
                          new ImageIcon("assets/unknown.png"));
        
        buyTurnip.setSeedIdx(CropInfo.TURNIP);
        buyCarrot.setSeedIdx(CropInfo.CARROT);
        buyPotato.setSeedIdx(CropInfo.POTATO);
        buyRose.setSeedIdx(CropInfo.ROSE);
        buyTurnipFlower.setSeedIdx(CropInfo.TURNIP_FLOWER);
        buySunflower.setSeedIdx(CropInfo.SUNFLOWER);
        buyMangoTree.setSeedIdx(CropInfo.MANGO);
        buyAppleTree.setSeedIdx(CropInfo.APPLE);
        
//        setLayout(new FlowLayout(FlowLayout.CENTER));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        add(buyTurnip);
        add(buyCarrot);
        add(buyPotato);
        add(buyRose);
        add(buyTurnipFlower);
        add(buySunflower);
        add(buyMangoTree);
        add(buyAppleTree);
        add(new SeedItem("Mystery", "You'll never know"));
    }

    public void setGui(GUIControl guiControl) {
        buyTurnip.setGui(guiControl);
        buyPotato.setGui(guiControl);
        buyCarrot.setGui(guiControl);
        buyRose.setGui(guiControl);
        buyTurnipFlower.setGui(guiControl);
        buySunflower.setGui(guiControl);
        buyMangoTree.setGui(guiControl);
        buyAppleTree.setGui(guiControl);
    }
    
    
}

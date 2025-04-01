/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ccprog3.myfarm.guinterface;

import com.ccprog3.myfarm.GUIControl;
import com.ccprog3.myfarm.model.Ground;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author John Joseph F. Giron
 */
public class GroundPanel extends JPanel{
    private String assetString = "assets/dirt.png";
    
    GroundButton groundButton[][];
    Ground clickedGround;
    GUIControl guiControl;
    Dimension iconSize = new Dimension(32, 32);
    ImageIcon groundIcon = new ImageIcon(assetString);
    
    public GroundPanel(GUIControl guiControl) {
        this.guiControl = guiControl;
        Ground ground[][] = guiControl.getAction().getGame().getBoard();
        
        int row = ground.length;
        int column = ground[0].length;
        
        setLayout(new GridLayout(row, column));
        
        if (GUIControl.debug)
            setBorder(BorderFactory.createLineBorder(Color.black, 5));
        
        groundButton = new GroundButton[row][column];
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                groundButton[i][j] = new GroundButton(this, ground[i][j]);
                groundButton[i][j].setPreferredSize(iconSize);
                groundButton[i][j].setVisible(true);
                System.out.println("row size = \t" + row);
                System.out.println("column size = \t" + column);
                System.out.println("["+ ground[i][j].getRow() + ", " + ground[i][j].getColumn() + "]");
                System.out.println("-------");
                groundButton[i][j].updateState(guiControl.getAction());
                add(groundButton[i][j]);
            }
        }
    }
    
    public void sendGround(GroundButton ground) {
        int row = ground.getRow();
        int column = ground.getColumn();
        clickedGround = guiControl.getAction().getGame().getBoard()[row][column];
        guiControl.interactGround(ground);
    }

    public void updateGround() {
        int row = groundButton.length;
        int column = groundButton[0].length;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                groundButton[i][j].updateState(guiControl.getAction());
            }
        }
    }

    public Ground getClickedGround() {
        return clickedGround;
    }
}

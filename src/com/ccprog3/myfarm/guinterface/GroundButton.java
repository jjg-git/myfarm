/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ccprog3.myfarm.guinterface;

import com.ccprog3.myfarm.Action;
import com.ccprog3.myfarm.model.CropInfo;
import com.ccprog3.myfarm.model.Crop;
import com.ccprog3.myfarm.model.Ground;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author John Joseph F. Giron
 */
public class GroundButton extends JButton implements ActionListener{
    private int row;
    private int column;
    
    private ImageIcon dirtIcon[];
    private ImageIcon cropIcon[][];
    private ImageIcon flowerIcon[][];
    private ImageIcon witheredIcon;

    private GroundPanel panel;
    
    private ImageIcon rockIcon;
    
    private int state;
    
    public static final int DRY = 0;
    public static final int ROCK = 1;
    public static final int PLOWED = 2;
    public static final int PLANT = 4;
    public static final int HARVEST = 5;
    public static final int WITHERED = 6;

    public GroundButton(GroundPanel panel, Ground ground) {
        this.panel = panel;
        this.row = ground.getRow();
        this.column = ground.getColumn();

        setToolTipText("[" + row + "][" + column + "]");

        dirtIcon = new ImageIcon[]{
            new ImageIcon("assets/dirt.png"),
            new ImageIcon("assets/farmland_dry.png"),
            new ImageIcon("assets/farmland_wet.png")
        };

        cropIcon = new ImageIcon[][]{
            // turnip
            {new ImageIcon("assets/crop_stage_0.png"), // stage 0
             new ImageIcon("assets/crop_stage_1.png"), // stage 1
             new ImageIcon("assets/crop_stage_2.png"), // stage 2
             new ImageIcon("assets/turnip_crop.png")},       // stage 3
            // carrot
            {new ImageIcon("assets/crop_stage_0.png"), // stage 0
             new ImageIcon("assets/crop_stage_1.png"), // stage 1
             new ImageIcon("assets/crop_stage_2.png"), // stage 2
             new ImageIcon("assets/carrot_crop.png")},       // stage 3
            // potato
            {new ImageIcon("assets/potatoes_stage_0.png"), // stage 0
             new ImageIcon("assets/potatoes_stage_1.png"), // stage 1
             new ImageIcon("assets/potatoes_stage_2.png"), // stage 2
             new ImageIcon("assets/potatoes_stage_3.png")}, // stage 3
            // rose
            {new ImageIcon("assets/flower_stage_0.png"), // stage 0
             new ImageIcon("assets/flower_stage_1.png"), // stage 1
             new ImageIcon("assets/flower_stage_2.png"), // stage 2
             new ImageIcon("assets/rose.png")}, // stage 3
            // turnip flower
            {new ImageIcon("assets/flower_stage_0.png"), // stage 0
             new ImageIcon("assets/flower_stage_1.png"), // stage 1
             new ImageIcon("assets/flower_stage_2.png"), // stage 2
             new ImageIcon("assets/flower_turnip.png")}, // stage 3
        };

        rockIcon = new ImageIcon("assets/rock.png");

        witheredIcon = new ImageIcon("assets/withered.png");

        setIcon(dirtIcon[0]);
        addActionListener(this);
    }

    public void updateState(Action action) {
        Ground current = action.getGame().getBoard()[row][column];
        Crop crop = current.getSeed();
        
        // Update the icon
        if (current.getHasRock()) {
            state = ROCK;
        }
        else {
            if (current.isPlowed()) {
                if (current.getSeed() == null) {
                    state = PLOWED;
                }
                else {
                    state = PLANT;
                    if (current.getSeed().isHarvestable()) {
                        state = HARVEST;
                    }
                    if (current.getSeed().isWithered()) {
                        state = WITHERED;
                    }
                }
            }
            else {
                state = DRY;
            }
        }
        
        updateIcon(action);
    }
    
    private void updateIcon(Action action) {
        Ground current = action.getGame().getBoard()[row][column];
        Crop crop = current.getSeed();
        int idx;
        int lifespan;
        
        // Update the icon
        switch (state) {
            case DRY -> setIcon(dirtIcon[0]);
            case ROCK -> setIcon(rockIcon);
            case PLOWED -> setIcon(dirtIcon[1]);
            case PLANT ->  {
                idx = crop.getCropIdx();
                lifespan = (int)Math.ceil(((double)crop.getDayPassed() / (CropInfo.maxHarvestTimeList[idx] + 1)) * 3);
//                JOptionPane.showMessageDialog(null, (double)(crop.getDayPassed() / (CropInfo.maxHarvestTimeList[idx] + 1)) * 3);
//                JOptionPane.showMessageDialog(null, "days: " + crop.getDayPassed() + "/" + CropInfo.maxHarvestTimeList[idx]);
//                JOptionPane.showMessageDialog(null, (double)crop.getDayPassed() / CropInfo.maxHarvestTimeList[idx]);
                setIcon(cropIcon[idx][lifespan]);
            }
            case HARVEST -> {
                idx = crop.getCropIdx();
                setIcon(cropIcon[idx][3]);
            }
            case WITHERED -> setIcon(witheredIcon);
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        panel.sendGround(this);
    }

    public int getState() {
        return state;
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ccprog3.myfarm.guinterface;

import com.ccprog3.myfarm.Action;
import com.ccprog3.myfarm.GUIControl;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * The west panel with tools and actions
 * @author John Joseph F. Giron
 */
public class ToolsPanel extends JPanel{
    private RadioIcon hoe = new RadioIcon("Hoe", "assets/hoe.png");
    private RadioIcon pickaxe = new RadioIcon("Pickaxe", "assets/pickaxe.png");
    private RadioIcon shovel = new RadioIcon("Shovel", "assets/shovel.png");
    
    private CheckBoxIcon waterBucket =
            new CheckBoxIcon("Water", "assets/bucket_water.png");
    private SpinnerNumberModel waterRange = new SpinnerNumberModel(0, 0, 7, 1);
    
    private CheckBoxIcon fertilizer = 
            new CheckBoxIcon("Fertilizer", "assets/fertilizer.png");
    private SpinnerNumberModel fertilizeRange = new SpinnerNumberModel(0, 0, 7, 1);
    
    
    private ButtonGroup toolsGroup = new ButtonGroup();
    
    private JPanel waterFertilizePanel;
    
    private JButton nextDayButton;
    private JButton registerButton;

    private JPanel registerBenefitsPanel;
    private LVLUp costReduceLabel;
    private LVLUp profitRaiseLabel;
    private LVLUp waterBonusLabel;
    private LVLUp fertilizeBonusLabel;

    /**
     * Integer index for hoe tool
     */
    public static final int HOE = 0;

    /**
     * Integer index for pickaxe tool
     */
    public static final int PICKAXE = 2;

    /**
     * Integer index for shovel tool
     */
    public static final int SHOVEL = 3;
    
    private final String hoeCommand = "HOE";
    private final String waterCommand = "WATER";
    private final String fertilizeCommand = "FERTILIZE";
    private final String pickaxeCommand = "PICKAXE";
    private final String shovelCommand = "SHOVEL";
    
    private boolean water = false;
    private boolean fertilize = false;
    
    private GUIControl guiControl;
    
    private int toolCurrent;

    /**
     *
     */
    public ToolsPanel() {
        this.registerButton = new JButton("Register");
        this.registerBenefitsPanel = new JPanel();
        this.nextDayButton = new JButton("Advance Day");
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        if (GUIControl.debug)
            setBorder(BorderFactory.createLineBorder(Color.yellow, 10));
        
        this.toolCurrent = HOE;
        this.waterFertilizePanel = new JPanel();
        this.waterFertilizePanel.setLayout(
                new BoxLayout(waterFertilizePanel, BoxLayout.Y_AXIS)
        );
        
        setupGroups();
        
        hoe.getRadio().setSelected(true);
        hoe.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        add(hoe);
        
        
        JPanel waterPanel = new JPanel();
        JPanel fertilizePanel = new JPanel();
        
        waterPanel.setLayout(new BoxLayout(waterPanel, BoxLayout.PAGE_AXIS));
        fertilizePanel.setLayout(new BoxLayout(fertilizePanel, BoxLayout.PAGE_AXIS));

        waterRange = new SpinnerNumberModel(0, 0, 7, 1);
        fertilizeRange = new SpinnerNumberModel(0, 0, 7, 1);
        
        JSpinner waterTimes = new JSpinner(waterRange);
        JSpinner fertilizeTimes = new JSpinner(fertilizeRange);
        
        waterTimes.setMaximumSize(new Dimension(150, 20));
        fertilizeTimes.setMaximumSize(new Dimension(150, 20));
        
        waterPanel.add(waterBucket);
        waterPanel.add(waterTimes);
        fertilizePanel.add(fertilizer);
        fertilizePanel.add(fertilizeTimes);
        
        this.waterFertilizePanel.add(waterPanel);
        this.waterFertilizePanel.add(fertilizePanel);
        waterFertilizePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(waterFertilizePanel);
        
        water = waterBucket.getCheckBox().isSelected();
        fertilize = fertilizer.getCheckBox().isSelected();
        
        
        pickaxe.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(pickaxe);
        
        shovel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(shovel);
        
        nextDayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                guiControl.useBed();
            }
        });
        add(nextDayButton);
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                guiControl.playerRegister();
            }
        });
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showRegisterBenefits();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hideRegisterBenefits();
            }
        });
        add(registerButton);
        
        
        hoe.getRadio().addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                toolCurrent = HOE;
                waterBucket.getCheckBox().setEnabled(hoe.getRadio().isSelected());
                fertilizer.getCheckBox().setEnabled(hoe.getRadio().isSelected());
            }
        });

        pickaxe.getRadio().addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                toolCurrent = PICKAXE;
            }
        });

        shovel.getRadio().addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                toolCurrent = SHOVEL;
            }
        });

        waterBucket.getCheckBox().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                water = (itemEvent.getStateChange() == 1);
                System.out.println("water = " + water);
            }
        });

        fertilizer.getCheckBox().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                fertilize = (itemEvent.getStateChange() == 1);
                System.out.println("fertilize = " + fertilize);
            }
        });

        costReduceLabel = new LVLUp(Action.COST_REDUCT, LVLUp.DEDUCT);
        profitRaiseLabel = new LVLUp(Action.BONUS_PER_PRODUCE, LVLUp.RAISE);
        waterBonusLabel = new LVLUp(Action.WATER_BONUS_LIMIT, LVLUp.RAISE);
        fertilizeBonusLabel = new LVLUp(Action.FERTILIZER_BONUS_LIMIT, LVLUp.RAISE);

//        registerBenefitsPanel.setLayout(new BoxLayout(registerBenefitsPanel, BoxLayout.PAGE_AXIS));
        registerBenefitsPanel.setLayout(new BoxLayout(registerBenefitsPanel, BoxLayout.PAGE_AXIS));
//        registerBenefitsPanel.setLayout(new FlowLayout());
        registerBenefitsPanel.add(costReduceLabel);
        registerBenefitsPanel.add(profitRaiseLabel);
        registerBenefitsPanel.add(waterBonusLabel);
        registerBenefitsPanel.add(fertilizeBonusLabel);
//        registerBenefitsPanel.setMinimumSize(new Dimension(100, 20));
        registerBenefitsPanel.setVisible(false);
        registerBenefitsPanel.setAlignmentX(Container.LEFT_ALIGNMENT);
        add(registerBenefitsPanel);

    }

    private void hideRegisterBenefits() {
        registerBenefitsPanel.setVisible(false);
    }

    private void showRegisterBenefits() {
        registerBenefitsPanel.setVisible(true);
    }

    private void setupToolsGroup() {
        toolsGroup.add(hoe.getRadio());
        toolsGroup.add(pickaxe.getRadio());
        toolsGroup.add(shovel.getRadio());
    }

    private void setupGroups() {
        setupToolsGroup();
    }
    
    
    public void setTool(int tool) {
        toolCurrent = tool;
    }
    
    public int getTool() {
        return toolCurrent;
    }

    public void setGUI(GUIControl guiControl) {
        this.guiControl = guiControl;
    }

    public boolean isWater() {
        return water;
    }

    public boolean isFertilize() {
        return fertilize;
    }
    
    public int getWaterCount() {
        return (int)waterRange.getValue();
    }
    
    public int getFertilizeCount() {
        return (int)fertilizeRange.getValue();
    }

    public void updateBenefit(int playerLVL) {
        costReduceLabel.updateLabel(playerLVL);
        profitRaiseLabel.updateLabel(playerLVL);
        waterBonusLabel.updateLabel(playerLVL);
        fertilizeBonusLabel.updateLabel(playerLVL);
    }
}

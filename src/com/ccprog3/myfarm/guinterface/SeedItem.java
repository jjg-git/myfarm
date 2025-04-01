/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ccprog3.myfarm.guinterface;

import com.ccprog3.myfarm.GUIControl;
import com.ccprog3.myfarm.model.CropInfo;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author John Joseph F. Giron
 */
public class SeedItem extends JPanel{
    private int seedIdx;
    private GUIControl guiControl;
    private JTextArea label = new JTextArea();
    
    private String seedInfo;
    
    public SeedItem(String name, String description, ImageIcon icon) {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        
        seedInfo = description;
        
        JPanel titlePanel = new JPanel();
        JPanel itemPanel = new JPanel();
        JLabel itemIcon = new JLabel(icon);
        JLabel itemName = new JLabel(name);
        JButton buyButton = new JButton("Buy!");
        label = new JTextArea();
        JScrollPane labelScroll = new JScrollPane(label);
        
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
//        titlePanel.setBorder(BorderFactory.createLineBorder(Color.red, 2));
        titlePanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.PAGE_AXIS));
//        itemPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
        
        itemIcon.setAlignmentX(Component.LEFT_ALIGNMENT);
        itemName.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelScroll.getVerticalScrollBar().setUnitIncrement(16);
        labelScroll.getHorizontalScrollBar().setUnitIncrement(16);
        
        titlePanel.add(itemIcon);
        titlePanel.add(itemName);
        itemPanel.add(titlePanel);
        itemPanel.add(labelScroll);
        
        
        JPanel buyPanel = new JPanel();
        buyPanel.setLayout(new BoxLayout(buyPanel, BoxLayout.PAGE_AXIS));
//        buyPanel.setBorder(BorderFactory.createLineBorder(Color.green, 2));
        buyButton.setAlignmentY(Component.TOP_ALIGNMENT);
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (guiControl != null) {
                    guiControl.buySeed(SeedItem.this);
                }
                else {
                    System.out.println("You forgot to setGui().");
                }
            }
        });
        buyPanel.add(buyButton);
        buyPanel.add(Box.createVerticalGlue());
        
        label.setPreferredSize(new Dimension(200,100));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setEditable(false);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        label.setLineWrap(true);
        
        
        add(itemPanel);
        add(buyPanel);
    }
    
    public SeedItem(String name, String description) {
        this(name, description, new ImageIcon("assets/unknown.png"));
        label.setText(description);
    }
    
    public void setSeedIdx (int idx) {
        seedIdx = idx;
    }

    public int getSeedIdx() {
        return seedIdx;
    }

    public void setGui(GUIControl guiControl) {
        this.guiControl = guiControl;
        String info = String.format(
                        """
                        \n
                        Cost: %d %s
                        Sells: %d %s
                        Yields %d - %d harvests
                        EXP gain: %.2f"""
                        , CropInfo.seedCost[seedIdx],
                        guiControl.getAction().getGame().getNameCoin(),
                        CropInfo.sellPiece[seedIdx],
                        guiControl.getAction().getGame().getNameCoin(),
                        CropInfo.produceRange[seedIdx][CropInfo.MIN],
                        CropInfo.produceRange[seedIdx][CropInfo.MAX],
                        CropInfo.expYield[seedIdx]);
        
        label.setText(seedInfo + info);
    }
}

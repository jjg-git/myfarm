/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ccprog3.myfarm.guinterface;

import com.ccprog3.myfarm.GUIControl;
import com.ccprog3.myfarm.model.Player;

import javax.swing.ButtonModel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;

/**
 *
 * @author John Joseph F. Giron
 */
public class InventoryPanel extends JPanel{
    public InventoryItem slots[];
    private final int slotNumber;
    private int selectedSlot = -1;
    
    public InventoryPanel() {
        this.slotNumber = 8;
        
        slots = new InventoryItem[slotNumber];
        ToggleButtonGroup group = new ToggleButtonGroup();
        for (int i = 0; i < slotNumber; i++) {
            slots[i] = new InventoryItem(i);
            group.add(slots[i].getButton());
            add(slots[i]);
            add(Box.createVerticalGlue());
        }
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
//        setLayout(new FlowLayout());
        
    }
    
    public void updateList(Player player) {
        for (InventoryItem slot : slots) {
            slot.updateItem(player);
        }
    }
    
    public void setGUI(GUIControl guiControl) {
        for (InventoryItem slot : slots) {
            slot.setGUI(guiControl);
        }
    }

    public class ToggleButtonGroup extends ButtonGroup {
        public void setSelected(ButtonModel model, boolean selected) {
            if (selected) {
                super.setSelected(model, selected);
            } else {
                clearSelection();
            }
        }
    }
}

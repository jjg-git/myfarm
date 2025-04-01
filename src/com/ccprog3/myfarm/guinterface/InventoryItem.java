package com.ccprog3.myfarm.guinterface;

import com.ccprog3.myfarm.model.CropInfo;
import com.ccprog3.myfarm.GUIControl;
import com.ccprog3.myfarm.model.Player;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * The item of the seed inventory list
 * @author John Joseph F. Giron
 */
public class InventoryItem extends JPanel implements ItemListener{
    private ImageIcon icon;
    private final JToggleButton button;

    private final int seedIdx;

    private GUIControl guiControl;

    /**
     * The constructor of inventory item
     * @param idx The index of the seed
     */
    public InventoryItem(int idx) {
        seedIdx = idx;
        switch(seedIdx) {
            case 0 -> icon = new ImageIcon("assets/turnip.png");
            case 1 -> icon = new ImageIcon("assets/carrot.png");
            case 2 -> icon = new ImageIcon("assets/potato.png");
            case 3 -> icon = new ImageIcon("assets/rose.png");
            case 4 -> icon = new ImageIcon("assets/flower_turnip.png");
            case 5 -> icon = new ImageIcon("assets/sunflower.png");
            case 6 -> icon = new ImageIcon("assets/unknown.png");
            case 7 -> icon = new ImageIcon("assets/unknown.png");
        }
        button = new JToggleButton();
        JLabel nameLabel = new JLabel();
        
        button.setIcon(icon);
        button.addItemListener(this);
//        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameLabel.setText(CropInfo.seedList[idx]);
//        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        quantityLabel = new JLabel();
//        quantityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        add(button);
        add(nameLabel);
//        add(quantityLabel);
        
    }

    /**
     * Updates the GUI
     * @param player The player's data
     */
    public void updateItem(Player player) {
        int quantity = player.getSeedInventory()[seedIdx];
        
//        quantityLabel.setText(String.format(" x %d", quantity));
        button.setEnabled(quantity != 0);
        button.setText(String.format(" %d", quantity));
        
        button.setMinimumSize(new Dimension(500, button.getMaximumSize().height));
    }

    /**
     * Gathers GUI Controller.
     * @param guiControl The GUI Controller.
     */
    public void setGUI(GUIControl guiControl) {
        this.guiControl = guiControl;
    }


    /**
     * Returns the toggle button
     * @return the button
     */
    public JToggleButton getButton() {
        return button;
    }

    /**
     * Changes the game's chosen seed to plant.
     * @param itemEvent for event listener
     */
    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        int state = itemEvent.getStateChange();

        if (guiControl != null) {
            switch (state) {
                case ItemEvent.SELECTED -> {
                    System.out.println(seedIdx);
                    guiControl.chooseSeed(seedIdx);
                }
                case ItemEvent.DESELECTED -> guiControl.chooseSeed(-1);
            }
        }
    }
}

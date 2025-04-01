package com.ccprog3.myfarm.guinterface;

import com.ccprog3.myfarm.Action;
import com.ccprog3.myfarm.model.Player;

import javax.swing.*;
import java.awt.*;
import java.nio.channels.spi.AbstractInterruptibleChannel;

public class LVLUp extends JPanel {

    public static final int RAISE = 0;
    public static final int DEDUCT = 1;
    private JLabel benefit;

    private int LVLUpIdx;

    private int change;
    public LVLUp(int LVLUpIdx, int change) {
        ImageIcon imageIcon = new ImageIcon();
        JLabel icon = new JLabel();
        benefit = new JLabel();
        this.LVLUpIdx = LVLUpIdx;
        this.change = change;

        switch (LVLUpIdx) {
            case Action.COST_REDUCT -> imageIcon.setImage(new ImageIcon("assets/seed_icon.png").getImage());
            case Action.BONUS_PER_PRODUCE -> imageIcon.setImage(new ImageIcon("assets/profit_rise_icon.png").getImage());
            case Action.WATER_BONUS_LIMIT -> imageIcon.setImage(new ImageIcon("assets/bucket_water_icon.png").getImage());
            case Action.FERTILIZER_BONUS_LIMIT -> imageIcon.setImage(new ImageIcon("assets/fertilizer_icon.png").getImage());
        }


//        icon.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        benefit.setBorder(BorderFactory.createLineBorder(Color.green, 2));
//        setBorder(BorderFactory.createLineBorder(Color.red, 2));

        icon.setIcon(imageIcon);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(icon);
        add(benefit);
    }

    public void updateLabel(int playerLVL) {
        int benefits = Action.levelUpBenefits[playerLVL][LVLUpIdx];
        switch (change) {
            case RAISE -> benefit.setText(String.format("+ %d", benefits));
            case DEDUCT -> benefit.setText(String.format("- %d", benefits));
        }
    }
}

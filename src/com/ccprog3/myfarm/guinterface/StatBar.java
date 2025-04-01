/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ccprog3.myfarm.guinterface;

import com.ccprog3.myfarm.Game;
import com.ccprog3.myfarm.model.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.ccprog3.myfarm.guinterface.*;

/**
 *
 * @author John Joseph F. Giron
 */
public class StatBar extends JPanel{
    private JLabel coinLabel;
    private JLabel dayLabel;
    private JLabel levelNumberLabel;
    private JLabel levelStatusLabel;
    private JLabel EXPLabel;
    
    
    
    public StatBar() {
        JPanel coinPanel = new JPanel();
        JPanel dayPanel = new JPanel();
        JPanel levelPanel = new JPanel();
        
        JLabel coinIcon = new JLabel(new ImageIcon("assets/coin.png"));
        coinLabel = new JLabel();
        
        coinPanel.add(coinIcon);
        coinPanel.add(coinLabel);
        
        dayLabel = new JLabel();
        dayPanel.add(dayLabel);
        
        
        levelNumberLabel = new JLabel();
        levelStatusLabel = new JLabel();
        EXPLabel = new JLabel();
        levelPanel.add(levelNumberLabel);
        levelPanel.add(levelStatusLabel);
        levelPanel.add(EXPLabel);
        
        setBorder(BorderFactory.createLineBorder(Color.red, 2));
//        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
//        setLayout(new FlowLayout(FlowLayout.CENTER));
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setMaximumSize(new Dimension(getMaximumSize().width, 16));
        
        add(coinPanel);
        add(dayPanel);
        add(levelPanel);
    }
    
    public void updateInfo(Game game) {
        Player player = game.getPlayer();
        updateCoin(player, game);
        updateDay(game);
        updateLevel(player);
    }
    
    private void updateCoin(Player player, Game game) {
        coinLabel.setText(String.format("%.2f %s", player.getCoins(),
                                         game.getNameCoin()));
    }
    
    
    private void updateDay(Game game) {
        dayLabel.setText(String.format("%s", game.getDay()));
    }
    
    
    private void updateLevel(Player player) {
        String lvl = String.format("%d", player.getLevel());
        String exp = String.format("%.2f", player.getEXP());
        
        String type = Player.farmerTypeString[player.getFarmerType()];
        
        levelNumberLabel.setText(lvl);
        levelStatusLabel.setText(type);
        EXPLabel.setText(exp);
    }
}
